package dev.socialnetwork.service;

import dev.socialnetwork.entity.Account;
import dev.socialnetwork.exception.*;
import dev.socialnetwork.jwt.Jwt;
import dev.socialnetwork.jwt.PasswordRecovery;
import dev.socialnetwork.jwt.Token;
import dev.socialnetwork.model.Status;
import dev.socialnetwork.service.impl.AccountServiceImpl;
import dev.socialnetwork.jwt.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthService {
    private final AccountServiceImpl accountService;
    private final String accessTokenSecret;
    private final String refreshTokenSecret;
    private final Long accessTokenValidity;
    private final Long refreshTokenValidity;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Autowired
    public AuthService(@Value("${application.security.access-token-secret}") String accessTokenSecret,
                       @Value("${application.security.refresh-token-secret}") String refreshTokenSecret,
                       @Value("${application.security.access-token-validity}") Long accessTokenValidity,
                       @Value("${application.security.refresh-token-validity}") Long refreshTokenValidity,
                       AccountServiceImpl accountService,
                       PasswordEncoder passwordEncoder,
                       MailService mailService
    ) {
        this.accountService = accountService;
        this.accessTokenSecret = accessTokenSecret;
        this.refreshTokenSecret = refreshTokenSecret;
        this.passwordEncoder = passwordEncoder;
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
        this.mailService = mailService;
    }

    public Account register(String email, String firstName, String lastName, String password, String passwordConfirm) {
        if (!accountService.findByEmail(email).isEmpty()) {
            throw new EmailAlreadyExistsError();
        }
        if (!password.equals(passwordConfirm))
            throw new PasswordDoNotMatchError();
        return accountService.save(
                Account.of(email, firstName, lastName,
                        passwordEncoder.encode(password),
                        Set.of(accountService.findRoleByName("ROLE_USER")), Status.ACTIVE)
        );
    }

    public Login login(String email, String password) {
        Account account = accountService.findByEmail(email).orElseThrow(InvalidCredentialsError::new);
        if (!passwordEncoder.matches(password, account.getPassword()))
            throw new InvalidCredentialsError();
        var login = Login.of(
                account.getId(),
                accessTokenSecret,accessTokenValidity,
                refreshTokenSecret, refreshTokenValidity);
        var refreshJwt = login.getRefreshToken();
        Token token = new Token(refreshJwt.getToken(),refreshJwt.getIssueAt(),refreshJwt.getExpiration());
        token.setAccount(account);
        account.addToken(token);
        accountService.save(account);
        return login;
    }

    public Account getAccountFromToken(String token) {
        return accountService.findById(Jwt.from(token, accessTokenSecret).getAccountId())
                .orElseThrow(AccountNotFoundError::new);
    }

    public Login refreshAccess(String refreshToken) {
        var refreshJwt = Jwt.from(refreshToken, refreshTokenSecret);

        accountService.findByIdAndTokensRefreshTokenAndTokensExpiredAtGreaterThan(refreshJwt.getAccountId(), refreshJwt.getToken(), refreshJwt.getExpiration())
                .orElseThrow(UnauthenticatedError::new);

        return Login.of(refreshJwt.getAccountId(), accessTokenSecret, accessTokenValidity, refreshJwt);
    }

    public boolean logout(String refreshToken) {
        var refreshJwt = Jwt.from(refreshToken, refreshTokenSecret);

        var account = accountService.findById(refreshJwt.getAccountId())
                .orElseThrow(UnauthenticatedError::new);

        var tokenIsRemoved = account.removeTokenIf(token -> Objects.equals(token.getRefreshToken(), refreshToken));


        if (tokenIsRemoved) {
            accountService.save(account);
        }

        return tokenIsRemoved;

    }

    public void forgot(String email, String originUrl) {
        System.out.println(email);
        var token = UUID.randomUUID().toString().replace("-", "");
        var account = accountService.findByEmail(email)
                .orElseThrow(AccountNotFoundError::new);
        PasswordRecovery passwordRecovery = new PasswordRecovery(token);
        passwordRecovery.setAccount(account);
        account.addPasswordRecovery(passwordRecovery);

        mailService.sendForgotMessage(email, token, originUrl);
        try {
            accountService.save(account);
        }catch (RuntimeException e){
            System.out.println("bad request");;
        }
    }
    public Boolean reset(String password, String passwordConfirm, String token) {
        if (!Objects.equals(password, passwordConfirm)) {
            throw new PasswordDoNotMatchError();
        }

        var account = accountService.findByPasswordRecoveriesToken(token)
                .orElseThrow(InvalidLinkError::new);

        var passwordRecoveryIsRemoved = account.removePasswordRecoveryIf(passwordRecovery ->
                Objects.equals(passwordRecovery.getToken(), token));

        if (passwordRecoveryIsRemoved) {
            account.setPassword(passwordEncoder.encode(password));
            accountService.save(account);
        }

        return passwordRecoveryIsRemoved;
    }
}
