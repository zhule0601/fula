package com.fula.handler;

import com.fula.model.system.FulaError;
import com.fula.model.system.FulaException;
import com.fula.model.system.ResultBody;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

/** 对所有 controller 请求进行结果包装 每个请求返回结果都是 ResultBody 包装 每个请求返回都有错误码 每个请求都有耗时日志 每个请求都有入参出参日志 */
@Component
@Order(1)
@Aspect
public class GlobalControllerHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalControllerHandler.class);

  @Around("execution(* com.fula.controller..*.*(..))")
  public Object aroundForController(ProceedingJoinPoint proceedingJoinPoint) {
    Object result;
    String methodName = "";
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    try {
      MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
      methodName = methodSignature.getMethod().getName();
      logger.info(
          "##Invoke begin. method:[{}],param:[{}],values:[{}]",
          methodName,
          Arrays.toString(methodSignature.getParameterNames()),
          Arrays.toString(proceedingJoinPoint.getArgs()));
      result = proceedingJoinPoint.proceed();
    } catch (FulaException e) {
      logger.error("FulaException", e);
      ResultBody<Boolean> resultBody = new ResultBody<>();
      resultBody.setResult(Boolean.FALSE);
      resultBody.setCode(e.getErrorCode());
      resultBody.setMessage(e.getErrorMsg());
      result = resultBody;
    } catch (Exception e) {
      logger.error("Exception", e);
      ResultBody<Boolean> resultBody = new ResultBody<>();
      resultBody.setResult(Boolean.FALSE);
      resultBody.setCode(FulaError.INTERNAL_SERVER_ERROR.getResultCode());
      resultBody.setMessage(FulaError.INTERNAL_SERVER_ERROR.getResultMsg());
      result = resultBody;
    } catch (Throwable throwable) {
      logger.error("throwable:", throwable);
      ResultBody<Boolean> resultBody = new ResultBody<>();
      resultBody.setResult(Boolean.FALSE);
      resultBody.setCode(FulaError.INTERNAL_SERVER_ERROR.getResultCode());
      resultBody.setMessage(FulaError.INTERNAL_SERVER_ERROR.getResultMsg());
      result = resultBody;
    }
    stopWatch.stop();
    if (result instanceof ResultBody) {
      if (!FulaError.SUCCESS.getResultCode().equals((((ResultBody) result).getCode()))) {
        logger.info(
            "##Invoke end. method:[{}] success. cost:[{}]ms. value:[{}].",
            methodName,
            stopWatch.getTotalTimeMillis(),
            ((ResultBody) result).getResult());
      } else {
        logger.info(
            "##Invoke end. method:[{}] failed. cost:[{}]ms. value:[{}].",
            methodName,
            stopWatch.getTotalTimeMillis(),
            ((ResultBody) result).getResult());
      }
    }
    return result;
  }
}
