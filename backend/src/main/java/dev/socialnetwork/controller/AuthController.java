package dev.socialnetwork.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.socialnetwork.entity.Account;
import dev.socialnetwork.service.AuthService;
import dev.socialnetwork.service.impl.AccountServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    record RegisterRequest(String email, @JsonProperty("first_name") String firstName,
                           @JsonProperty("last_name") String lastName, String password,
                           @JsonProperty("password_confirm") String passwordConfirm) {
    }

    record RegisterResponse(String email, @JsonProperty("first_name") String firstName,
                            @JsonProperty("last_name") String lastName) {
    }

    record LoginRequest(String email, String password) {
    }

    record LoginResponse(String token) {
    }

    record AccountResponse(Long id, String email, @JsonProperty("first_name") String firstName,
                           @JsonProperty("last_name") String lastName) {
    }


    @PostMapping("/register")
    public RegisterResponse registration(@RequestBody RegisterRequest registerRequest) {
        Account account = authService.register(
                registerRequest.email(),
                registerRequest.firstName(),
                registerRequest.lastName(),
                registerRequest.password(),
                registerRequest.passwordConfirm()
        );
        return new RegisterResponse(account.getEmail(), account.getFirstName(), account.getLastName());
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        var login = authService.login(loginRequest.email(), loginRequest.password());
        Cookie cookie = new Cookie("refresh_token", login.getRefreshToken().getToken());
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setPath("/api");
        response.addCookie(cookie);
        return new LoginResponse(login.getAccessToken().getToken());
    }

    @GetMapping("/account")
    public AccountResponse account(HttpServletRequest request) {
        var account = (Account) request.getAttribute("account");
        return new AccountResponse(account.getId(), account.getEmail(), account.getFirstName(),
                account.getLastName());
    }

    @PostMapping("/logout")
    public String logout(@CookieValue("refresh_token") String refreshToken, HttpServletResponse response) {

        authService.logout(refreshToken);

        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
        return "success";
    }
    record RefreshResponse(String token) {}

    @PostMapping("/refresh")
    public RefreshResponse refresh(@CookieValue("refresh_token") String refreshToken) {
        return new RefreshResponse(authService.refreshAccess(refreshToken).getAccessToken().getToken());
    }

    record ForgotRequest(String email) {
    }

    record ForgotResponse(String message) {
    }

    @PostMapping(value = "/forgot")
    public ForgotResponse forgot(@RequestBody ForgotRequest forgotRequest, HttpServletRequest request) {
        var originUrl = request.getHeader(HttpHeaders.ORIGIN);

        authService.forgot(forgotRequest.email(), originUrl);

        return new ForgotResponse("success");
    }

    record ResetResponse(String message) {}
    record ResetRequest(String password, @JsonProperty("password_confirm") String passwordConfirm, String token) {}

    @PostMapping(value = "/reset")
    public ResetResponse reset(@RequestBody ResetRequest request) {
        System.out.println(request);
        if (!authService.reset(request.password(), request.passwordConfirm(), request.token())) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "cannot reset password");
        }

        return new ResetResponse("success");
    }

}


