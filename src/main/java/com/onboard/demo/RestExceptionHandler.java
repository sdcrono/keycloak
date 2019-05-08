//package com.onboard.demo;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//
//@RestControllerAdvice
//public class RestExceptionHandler {
//
//    @ExceptionHandler(value = ResourceNotFoundException.class)
//    public ResponseEntity resourceNotFound(ResourceNotFoundException exception, WebRequest request) {
//        return notFound().build()
//    }
//}
