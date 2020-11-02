package com.fula.model.system;

public class FulaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected String errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;

    public FulaException() {
        super();
    }

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

    public FulaException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public FulaException(String errorCode, String errorMsg) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public FulaException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMessage() {
        return errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
