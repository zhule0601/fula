package com.fula.model.system;

import com.fula.interceptor.MDCInterceptor;
import lombok.Data;
import org.slf4j.MDC;

@Data
public class ResultBody<T> {

  public static final String SUCCESS_CODE = "0000";
  public static final String SUCCESS_MESSAGE = "SUCCESS";

  private String code = SUCCESS_CODE;
  private String message = SUCCESS_MESSAGE;
  private String requestId;

  private T result;

  public ResultBody() {
    requestId = MDC.get(MDCInterceptor.REQUEST_ID);
  }

  public ResultBody(T result) {
    requestId = MDC.get(MDCInterceptor.REQUEST_ID);
    this.result = result;
  }
}
