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

import java.util.Arrays;

@Component
@Order(1)
@Aspect
public class GlobalResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalResponseHandler.class);

    @Around("execution(* com.fula.controller..*.*(..))")
    public Object aroundForController(ProceedingJoinPoint proceedingJoinPoint) {
        Object result;
        try {
            long startInvoke = System.currentTimeMillis();
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            String msg = String.format("##Invoke method:[%s],param:[%s],values:[%s]",
                    methodSignature.getMethod().getName(),
                    Arrays.toString(methodSignature.getParameterNames()),
                    Arrays.toString(proceedingJoinPoint.getArgs()));
            logger.info(msg);
            result = proceedingJoinPoint.proceed();
            long cost = System.currentTimeMillis() - startInvoke;
            logger.info("[{}],[{}] success. cost:[{}]ms.", methodSignature.getMethod().getName(), null, cost);
        } catch (FulaException e) {
            ResultBody<Boolean> resultBody = new ResultBody<>();
            resultBody.setResult(Boolean.FALSE);
            resultBody.setCode(e.getErrorCode());
            resultBody.setMessage(e.getErrorMsg());
            result = resultBody;
        } catch (Exception e) {
            logger.error("", e);
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
        return result;
    }

}
