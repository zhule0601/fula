package com.fula.component;

import com.fula.component.model.FulaBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;


@Component
public class TgBotComponent {

    @Value("${bot.enable:false}")
    private boolean enableBot;
    @Value("${bot.real.name:fake}")
    private String botName;
    @Value("${bot.token:fake}")
    private String botToken;
    @Value("${bot.enable.proxy:false}")
    private boolean enableProxy;
    private static final String PROXY_HOST = "127.0.0.1";
    private static final int PROXY_PORT = 7890;


    public static TelegramBotsApi telegramBotsApi;

    @PostConstruct
    public void init() throws TelegramApiRequestException {
        if (enableBot) {
            ApiContextInitializer.init();
            // Set up Http proxy
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
            if (enableProxy) {
                botOptions.setProxyHost(PROXY_HOST);
                botOptions.setProxyPort(PROXY_PORT);
                // Select proxy type: [HTTP|SOCKS4|SOCKS5] (default: NO_PROXY)
                botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
            }
            // Register your newly created AbilityBot
            FulaBot fulaBot = new FulaBot(botToken, botName, botOptions);
            telegramBotsApi = new TelegramBotsApi();
            telegramBotsApi.registerBot(fulaBot);
        }
    }
}
