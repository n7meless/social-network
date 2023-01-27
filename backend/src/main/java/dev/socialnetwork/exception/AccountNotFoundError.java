package dev.socialnetwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AccountNotFoundError extends ResponseStatusException {
    public AccountNotFoundError() {
        super(HttpStatus.NOT_FOUND,"account not found");
    }
}
