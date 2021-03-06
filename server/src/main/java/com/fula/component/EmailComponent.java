package com.fula.component;

import io.github.biezhi.ome.OhMyEmail;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static io.github.biezhi.ome.OhMyEmail.SMTP_163;

@Component
public class EmailComponent {

    public static final Logger logger = LoggerFactory.getLogger(EmailComponent.class);

    @Value("${email.enable:false}")
    private boolean enableEmail;
    @Value("${email.from:fake}")
    private String fromEmail;
    @Value("${email.from.auth.code:fake}")
    private String fromEmailAuthCode;
    @Value("${email.default.to:fake}")
    private String toEmail;

    private boolean debug;
    public static String defaultToEmail;

    @PostConstruct
    public void init() {
        if (enableEmail) {
            OhMyEmail.config(SMTP_163(debug), fromEmail, fromEmailAuthCode);
            defaultToEmail = toEmail;
        }
    }

    public static void sendText(String subject, String content) {
        sendText(subject, content, defaultToEmail);
    }

    public static void sendText(String subject, String content, String toEmail) {
        if (StringUtils.isBlank(subject) || StringUtils.isBlank(content)) {
            logger.error("param is not legal。 please check param. subject:{}, content:{}, toEmail:{}",
                    subject, content, toEmail);
        }
        try {
            OhMyEmail.subject(subject)
                    .from("fula's email")
                    .to(toEmail)
                    .text(content)
                    .send();
        } catch (Exception e) {
            logger.error("send email error!", e);
        }
    }

}
