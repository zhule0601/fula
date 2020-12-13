package com.fula.component;

import com.fula.util.ProcessExecuteUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MysqlComponent {

    public static final String BIN_DIR = System.getProperty("app.bin");
    public static final String VAR_DIR = System.getProperty("java.io.tmpdir");
    public static final String BACKUP_DIR = VAR_DIR + "/backup";
    public static final String MYSQL_DUMP_COMMAND = BIN_DIR + "/cron/mysql-backup.sh";
    public static final String MYSQL_SEND_COMMAND = BIN_DIR + "/cron/tg.sh";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

    public static void backupMysql2TG(){
        //1. dump
        //2. send to TG
        String fileName = dateFormat.format(System.currentTimeMillis());
        dump(BACKUP_DIR, fileName);
        sendFile2TG(BACKUP_DIR + "/" + fileName, "zhu_fula_bot");
    }

    private static void dump(String filePath, String fileName) {
        List<String> cmd = new ArrayList<>();
        cmd.add("/bin/bash");
        cmd.add("-c");
        String command = String.format("%s %s %s %s", MYSQL_DUMP_COMMAND, "dump", filePath, fileName);
        cmd.add(command);
        ProcessExecuteUtil.exec(cmd);
    }

    private static void sendFile2TG(String filePath, String to) {
        List<String> cmd = new ArrayList<>();
        cmd.add("/bin/bash");
        cmd.add("-c");
        String command = String.format("%s %s %s %s", MYSQL_SEND_COMMAND, "send", to, filePath);
        cmd.add(command);
        ProcessExecuteUtil.exec(cmd);
    }

}
