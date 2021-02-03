package com.fula.model.system;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FulaException extends RuntimeException {

    protected String errorCode;
    protected String errorMsg;

    public FulaException(FulaError error) {
        super(error.getResultCode());
        this.errorCode = error.getResultCode();
        this.errorMsg = error.getResultMsg();
    }

    public FulaException(FulaError error, Throwable cause) {
        super(error.getResultCode(), cause);
        this.errorCode = error.getResultCode();
        this.errorMsg = error.getResultMsg();
    }

    public FulaException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
