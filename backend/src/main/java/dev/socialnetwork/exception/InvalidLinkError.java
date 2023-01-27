package dev.socialnetwork.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class InvalidLinkError extends ResponseStatusException {
    public InvalidLinkError() {
        super(HttpStatus.BAD_REQUEST, "invalid link error");
    }
}
