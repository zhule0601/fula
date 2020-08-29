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

    private boolean debug;
    @Value("${from.email}")
    private String fromEmail;
    @Value("${from.email.auth.code}")
    private String fromEmailAuthCode;
    @Value("${to.email}")
    private String toEmail;

    public static String defaultToEmail;

    @PostConstruct
    public void init() {
        OhMyEmail.config(SMTP_163(debug), fromEmail, fromEmailAuthCode);
        defaultToEmail = toEmail;
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
