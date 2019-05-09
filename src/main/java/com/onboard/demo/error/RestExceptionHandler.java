package com.onboard.demo.error;

import com.onboard.demo.common.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ExceptionDetail errorDetails = new ExceptionDetail(
                HttpStatus.NOT_FOUND.value(),
                new Error(ex.getMessage(), request.getDescription(false), new Date())
        );
        return new ResponseEntity<>(ResponseError.of(errorDetails), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> BadRequestException(BadRequestException ex, WebRequest request) {
        ExceptionDetail errorDetails = new ExceptionDetail(
                HttpStatus.BAD_REQUEST.value(),
                new Error(ex.getMessage(), request.getDescription(false),  new Date())
        );
        return new ResponseEntity<>(ResponseError.of(errorDetails), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExcpetionHandler(Exception ex, WebRequest request) {
        ExceptionDetail errorDetails = new ExceptionDetail(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Error(ex.getMessage(), request.getDescription(false), new Date())
        );
        return new ResponseEntity<>(ResponseError.of(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
