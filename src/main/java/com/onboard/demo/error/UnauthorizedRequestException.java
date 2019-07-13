package com.onboard.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedRequestException extends Exception {
    private static final long serialVersionUID = 1L;

    public UnauthorizedRequestException() {
        super();
    }
    public UnauthorizedRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    public UnauthorizedRequestException(String message) {
        super(message);
    }
    public UnauthorizedRequestException(Throwable cause) {
        super(cause);
    }
}
