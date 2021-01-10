package com.fula.scheduler;

import com.fula.component.AutoCheckInComponent;
import com.fula.component.MysqlComponent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Scheduled(cron = "${glados.checkin.cron}")
    public void GLADOSCheckIn(){
        AutoCheckInComponent.GLADOSCheckIn();
    }

    @Scheduled(cron = "${mysql.backup.cron}")
    public void MysqlDUMP(){
        MysqlComponent.backupMysql2TG();
    }
}
