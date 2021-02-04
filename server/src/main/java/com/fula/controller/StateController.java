package com.fula.controller;

import com.fula.component.AutoCheckInComponent;
import com.fula.component.MysqlComponent;
import com.fula.model.system.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zl
 * @description 用于测试,检查服务状态
 * @date 2020/8/28 19:14
 */
@Slf4j
@RestController
public class StateController {

  private static final long FULA_START_TIME = System.currentTimeMillis();

  @GetMapping("/state")
  public ResultBody<String> state() {
    String msg = String.format("system has bean started at %d", FULA_START_TIME);
    log.info(msg);
    return new ResultBody<>(msg);
  }

  @GetMapping("/glados/checkin")
  public ResultBody<String> gladosCheckin() {
    AutoCheckInComponent.GLADOSCheckIn();
    return new ResultBody("success");
  }

  @GetMapping("/mysql/dump")
  public ResultBody<String> MysqlDUMP() {
    MysqlComponent.backupMysql2TG();
    return new ResultBody("success");
  }
}
