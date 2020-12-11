package com.fula.component;

import com.fula.util.ProcessExecuteUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutoCheckInComponent {

    public static final String BIN_DIR = System.getProperty("app.bin");


//    public static final String GLADOS_CHECKIN_COMMAND = "curl 'https://glados.rocks/api/user/checkin' \\\n" +
//            "  -H 'authority: glados.rocks' \\\n" +
//            "  -H 'accept: application/json, text/plain, */*' \\\n" +
//            "  -H 'dnt: 1' \\\n" +
//            "  -H 'user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36' \\\n" +
//            "  -H 'content-type: application/json;charset=UTF-8' \\\n" +
//            "  -H 'origin: https://glados.rocks' \\\n" +
//            "  -H 'sec-fetch-site: same-origin' \\\n" +
//            "  -H 'sec-fetch-mode: cors' \\\n" +
//            "  -H 'sec-fetch-dest: empty' \\\n" +
//            "  -H 'referer: https://glados.rocks/console/checkin' \\\n" +
//            "  -H 'accept-language: zh-CN,zh;q=0.9' \\\n" +
//            "  -H 'cookie: __cfduid=d5299b52c968d92a81879d07368c540ea1596380255; _ga=GA1.2.1517707468.1596380257; koa:sess=eyJ1c2VySWQiOjEyNzEsIl9leHBpcmUiOjE2MjIzMDAyOTc1MzIsIl9tYXhBZ2UiOjI1OTIwMDAwMDAwfQ==; koa:sess.sig=FCG4MhgiwdflhGEusOooLFCOlzI; _gid=GA1.2.682121913.1596858762; _gat_gtag_UA_104464600_2=1' \\\n" +
//            "  --data-binary '{}' \\\n" +
//            "  --compressed ";

    public static final String GLADOS_CHECKIN_COMMAND = BIN_DIR + "/cron/tg.sh";

    public static void GLADOSCheckIn() {
        List<String> cmd = new ArrayList<>();
        cmd.add("/bin/bash");
        cmd.add("-c");
        String command = String.format("%s %s %s %s", GLADOS_CHECKIN_COMMAND, "msg", "glados_core_bot", "/checkin");
        cmd.add(command);
        ProcessExecuteUtil.exec(cmd);
    }
}
