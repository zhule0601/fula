package com.fula.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zl
 * @description 用于测试,检查服务状态
 * @date 2020/8/28 19:14
 */
@RestController
public class StateController {

    private static final long FULA_START_TIME = System.currentTimeMillis();
    public static final Logger logger = LoggerFactory.getLogger(StateController.class);

    @GetMapping("/state")
    public String state() {
        String msg = String.format("system has bean started at %d", FULA_START_TIME);
        logger.info(msg);
        return msg;
    }
}
