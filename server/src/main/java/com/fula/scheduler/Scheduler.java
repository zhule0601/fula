package com.fula.scheduler;

import com.fula.component.AutoCheckInComponent;
import com.fula.component.MysqlComponent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Scheduled(cron = "0 32 7 * * ?")
    public void GLADOSCheckIn(){
        AutoCheckInComponent.GLADOSCheckIn();
    }

    @Scheduled(cron = "0 35 7 * * ?")
    public void MysqlDUMP(){
        MysqlComponent.backupMysql2TG();
    }
}
