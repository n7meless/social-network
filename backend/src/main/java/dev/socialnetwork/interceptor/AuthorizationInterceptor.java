package dev.socialnetwork.interceptor;

import dev.socialnetwork.exception.NoBearerTokenError;
import dev.socialnetwork.exception.UnauthenticatedError;
import dev.socialnetwork.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    public AuthorizationInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null) {
            throw new UnauthenticatedError();
        }
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new NoBearerTokenError();
        }

        request.setAttribute("account", authService.getAccountFromToken(authorizationHeader.substring(7)));

        return true;
    }
}
