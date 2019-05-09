package com.onboard.demo.common;

public class ResponseError<T> extends Response {
    private T data;

    private ResponseError(T data) {
        super(false, null, null);
        this.data = data;
    }

    public static <T> ResponseError<T> of(T data) {
        return new ResponseError<>(data);
    }

    public T getData() {
        return data;
    }
}
