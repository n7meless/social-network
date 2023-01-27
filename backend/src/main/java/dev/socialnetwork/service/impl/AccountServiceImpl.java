package dev.socialnetwork.service.impl;

import dev.socialnetwork.entity.Account;
import dev.socialnetwork.jwt.Token;
import dev.socialnetwork.model.Role;
import dev.socialnetwork.repository.AccountRepository;
import dev.socialnetwork.repository.RoleRepository;
import dev.socialnetwork.repository.TokenRepository;
import dev.socialnetwork.service.AccountService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    TokenRepository tokenRepository;

    AccountRepository accountRepository;
    RoleRepository roleRepository;

    @Autowired
    public AccountServiceImpl(TokenRepository tokenRepository, AccountRepository accountRepository,
                              RoleRepository roleRepository) {
        this.tokenRepository = tokenRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Account> findById(long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Role findRoleByName(String name){
        return roleRepository.findByName(name);
    }

    public Optional<Account> findByIdAndTokensRefreshTokenAndTokensExpiredAtGreaterThan(Long id, String refreshToken, LocalDateTime expiredAt){
        return accountRepository.findByIdAndTokensRefreshTokenAndTokensExpiredAtGreaterThan(id, refreshToken, expiredAt);
    }
    public Optional<Account> findByPasswordRecoveriesToken(String token){
        return accountRepository.findByPasswordRecoveriesToken(token);
    }

}
