package com.fula.model.system;

public class ResultBody<T> {

    public static final String SUCCESS_CODE = "0000";
    public static final String SUCCESS_MESSAGE = "SUCCESS";

    private String code = SUCCESS_CODE;

    private String message = SUCCESS_MESSAGE;

    private String requestId;

    private T result;

    public ResultBody() {
    }

    public ResultBody(T result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
