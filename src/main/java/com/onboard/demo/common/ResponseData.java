package com.onboard.demo.common;

public class ResponseData<T> extends Response {
    private T data;

    private ResponseData(T data) {
        super(true, null, null);
        this.data = data;
    }

    public static <T> ResponseData<T> of(T data) {
        return new ResponseData<>(data);
    }

    public T getData() {
        return data;
    }
}
