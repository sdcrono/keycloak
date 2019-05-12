package com.onboard.demo.error;

import com.onboard.demo.common.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ExceptionDetail errorDetails = new ExceptionDetail(
                HttpStatus.NOT_FOUND.value(),
                new Error(ex.getMessage(), ex.getCause().getMessage(), new Date())
        );
        return new ResponseEntity<>(ResponseError.of(errorDetails), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> BadRequestException(BadRequestException ex, WebRequest request) {
        ExceptionDetail errorDetails = new ExceptionDetail(
                HttpStatus.BAD_REQUEST.value(),
                new Error(ex.getMessage(), ex.getCause().getMessage(),  new Date())
        );
        return new ResponseEntity<>(ResponseError.of(errorDetails), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ExceptionDetail errorDetails = new ExceptionDetail(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Error("There was an unexpected problem serving your request", ex.getMessage(), new Date())
        );
        return new ResponseEntity<>(ResponseError.of(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedRequestException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ResponseEntity<?> handleUnauthorizedRequestException(UnauthorizedRequestException ex) {
        ExceptionDetail errorDetails = new ExceptionDetail(
                HttpStatus.UNAUTHORIZED.value(),
                new Error(ex.getMessage(), ex.getCause().getMessage(),  new Date())
        );
        return new ResponseEntity<>(ResponseError.of(errorDetails), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(FORBIDDEN)
    public ResponseEntity<?> handleAccessDeniedRequestException(AccessDeniedException ex) {
        ExceptionDetail errorDetails = new ExceptionDetail(
                HttpStatus.FORBIDDEN.value(),
                new Error("There was a permission problem serving your request", ex.getMessage(),  new Date())
        );
        return new ResponseEntity<>(ResponseError.of(errorDetails), HttpStatus.FORBIDDEN);
    }
}
