package com.fula.util;

import com.fula.component.EmailComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author zl
 * @description shell 执行工具类
 * 1. 执行超时,兜底kill
 * 2. 执行报错,邮件通知
 * @date 2020/9/1 21:21
 */
public class ProcessExecuteUtil {

    public static final int SHELL_TIME_OUT = 10;
    public static final String DEFAULT_SUBJECT = "shell execute";
    public static final Logger logger = LoggerFactory.getLogger(ProcessExecuteUtil.class);

    public static String exec(List<String> command, Map<String, String> environment, int timeoutSecond, boolean notifyEmail) {
        String result = "";
        String basicInfo = "";
        List<String> emails = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            Map<String, String> pbEnvironment = pb.environment();
            if (environment != null && environment.size() > 0) {
                pbEnvironment.putAll(environment);
            }
            basicInfo = String.format("execute shell command:{%s}", command);
            Process process = pb.start();
            ShellOutPut shellOutPut = new ShellOutPut(process.getInputStream());
            FutureTask<String> futureTask = new FutureTask<>(shellOutPut);
            new Thread(futureTask).start();
            boolean finished = process.waitFor(timeoutSecond, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                // time out
                String msg = String.format("%s.timeout seconds:[%s],output:[%s], basicInfo:%s.",
                        DEFAULT_SUBJECT, timeoutSecond, futureTask.get(), basicInfo);
                logger.error(msg);
                emails.add(msg);
            } else if (process.exitValue() != 0) {
                // execute error
                String msg = String.format("%s.exitValue:[%s]. output:[%s], basicInfo:%s.",
                        DEFAULT_SUBJECT, process.exitValue(), futureTask.get(), basicInfo);
                logger.error(msg);
                emails.add(msg);
            } else {
                String msg = String.format("%s.success.output:[%s], basicInfo:%s.",
                        DEFAULT_SUBJECT, futureTask.get(), basicInfo);
                logger.info(msg);
            }
            result = futureTask.get();
        } catch (Exception e) {
            String msg = String.format("%s.error.output:[%s], basicInfo:%s.",
                    DEFAULT_SUBJECT, e.getMessage(), basicInfo);
            emails.add(msg);
            logger.error(msg, e);
        } finally {
            if (!CollectionUtils.isEmpty(emails) && notifyEmail) {
                emails.forEach(msg -> EmailComponent.sendText(DEFAULT_SUBJECT, msg));
            }
        }
        return result;
    }

    public static String exec(List<String> command, Map<String, String> env, int timeout) {
        return exec(command, env, timeout, true);
    }

    public static String exec(List<String> command, Map<String, String> env) {
        return exec(command, env, SHELL_TIME_OUT, true);
    }

    public static String exec(List<String> command) {
        return exec(command, null, SHELL_TIME_OUT, true);
    }

    static class ShellOutPut implements Callable<String> {
        private InputStream inputStream;

        public ShellOutPut(final InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public String call() {
            StringBuilder result = new StringBuilder();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(this.inputStream, StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } catch (Exception e) {
                logger.error("error occurred when execute shell command", e);
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            return result.toString();
        }
    }

}
