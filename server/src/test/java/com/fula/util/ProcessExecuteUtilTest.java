package com.fula.util;

import com.fula.FULAApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

//@ActiveProfiles("dev")
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = FULAApplication.class)
public class ProcessExecuteUtilTest {


    //@Test
    public void exec() {
        List<String> cmd = new ArrayList<>();
        cmd.add("/bin/bash");
        cmd.add("-c");
        cmd.add("dir");
        ProcessExecuteUtil.exec(cmd);
    }
}
