package com.cv.aircraft.telegram;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Initializer {
    public static void init() {
        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {
            botsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            log.error("Some problem with initializing bot", e);
            throw new RuntimeException(e);
        }
    }
}
