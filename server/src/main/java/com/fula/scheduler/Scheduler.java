package com.fula.scheduler;

import com.fula.component.AutoCheckInComponent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Scheduled(cron = "0 32 7 * * ?")
    public void GLADOSCheckIn(){
        AutoCheckInComponent.GLADOSCheckIn();
    }
}
