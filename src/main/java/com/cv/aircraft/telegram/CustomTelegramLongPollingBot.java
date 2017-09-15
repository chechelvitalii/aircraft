package com.cv.aircraft.telegram;

import com.cv.aircraft.telegram.handler.Handler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.bots.commands.BotCommand;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CustomTelegramLongPollingBot extends TelegramLongPollingCommandBot {
    static {
        // Initialize Api Context
        ApiContextInitializer.init();
    }

    @Value("${bot.token}")
    private String botToken;
    @Value("${bot.name}")
    private String botName;

    @Autowired
    private ApplicationContext context;

    private List<Handler> handlers = new ArrayList<>();

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasLocation()) {
            Message message = update.getMessage();
            handlers.forEach(handler -> handler.execute(message));
        }
        if (update.hasCallbackQuery()) {
            handlers.forEach(handler -> handler.execute(update.getCallbackQuery()));
        }
    }

    @PostConstruct
    private void init() {
        //register commands
        Map<String, BotCommand> botCommandMap = context.getBeansOfType(BotCommand.class);
        botCommandMap.forEach((commandName, botCommand) -> {
            log.info("Command {} was registered", commandName);
            register(botCommand);
        });
        //register handlers
        Map<String, Handler> locationHandlerMap = context.getBeansOfType(Handler.class);
        locationHandlerMap.forEach((handlerName, botHandler) -> {
            log.info("Handler {} was registered", handlerName);
            handlers.add(botHandler);
        });
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
