package com.cv.aircraft.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class BotInitializer {

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void  init() {
        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {
            botsApi.registerBot(new Bot(context));
        } catch (TelegramApiException e) {
            log.error("Some problem with initializing bot", e);
            throw new RuntimeException(e);
        }
    }
}
