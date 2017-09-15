package com.cv.aircraft.telegram;

import com.cv.aircraft.telegram.handler.AbstractCallbackHandler;
import com.cv.aircraft.telegram.handler.LocationHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.bots.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CustomTelegramLongPollingBot extends TelegramLongPollingCommandBot {

    @Autowired
    private ApplicationContext context;

    private List<LocationHandler> locationHandlers = new ArrayList<>();
    private List<AbstractCallbackHandler> callbackHandlers = new ArrayList<>();

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasLocation()) {
            locationHandlers.forEach(locationHandler -> locationHandler.execute(update.getMessage()));
        }
        if (update.hasCallbackQuery()) {
            callbackHandlers.forEach(callbackHandler -> callbackHandler.execute(update.getCallbackQuery()));
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
        Map<String, LocationHandler> locationHandlerMap = context.getBeansOfType(LocationHandler.class);
        locationHandlerMap.forEach((handleName, botHandle) -> {
            log.info("Handler {} was registered", handleName);
            locationHandlers.add(botHandle);
        });

        Map<String, AbstractCallbackHandler> callbackHandlerMap = context.getBeansOfType(AbstractCallbackHandler.class);
        callbackHandlerMap.forEach((handleName, botHandle) -> {
            log.info("Handler {} was registered", handleName);
            callbackHandlers.add(botHandle);
        });
    }
}
