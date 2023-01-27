package dev.socialnetwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NoBearerTokenError extends ResponseStatusException {

    public NoBearerTokenError() {
        super(HttpStatus.BAD_REQUEST, "no bearer refreshToken");
    }
}
