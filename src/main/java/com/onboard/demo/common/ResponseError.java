package com.onboard.demo.common;

public class ResponseError<T> extends Response {
    private T error;

    private ResponseError(T error) {
        super(false, null, null);
        this.error = error;
    }

    public static <T> ResponseError<T> of(T error) {
        return new ResponseError<>(error);
    }

    public T getData() {
        return error;
    }
}
