package com.onboard.demo.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private boolean success;
    private String code;
    private String message;

    public Response(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static Response success() {
        return new Response(true, null, null);
    }

    public static Response success(String message) {
        return new Response(true, message, null);
    }

    public static Response fail(String code, String message) {
        return new Response(false, code, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
