package com.onboard.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AccessDeniedRequestException extends Exception {
    private static final long serialVersionUID = 1L;

    public AccessDeniedRequestException() {
        super();
    }
    public AccessDeniedRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    public AccessDeniedRequestException(String message) {
        super(message);
    }
    public AccessDeniedRequestException(Throwable cause) {
        super(cause);
    }
}
