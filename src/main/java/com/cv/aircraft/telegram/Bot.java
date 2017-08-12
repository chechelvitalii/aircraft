package com.cv.aircraft.telegram;

import com.cv.aircraft.dto.AircraftInfo;
import com.cv.aircraft.facade.AirpalneInZoneFacade;
import com.cv.aircraft.facade.CommandFacade;
import com.cv.aircraft.service.aircraft.AircraftInfoService;
import com.cv.aircraft.service.telegram.PrepareMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {
    public static final String TOKEN = "391777415:AAGKD1VhZZEF5oBQw0WDiMpAK7d4fr0P0Bo";
    public static final String BOT_NAME = "K1evbot";

    static {
        // Initialize Api Context
        ApiContextInitializer.init();
    }

    @Autowired
    private CommandFacade commandFacade;
    @Autowired
    private PrepareMessageService prepareMessageService;
    @Autowired
    private AircraftInfoService aircraftInfoService;
    @Autowired
    private AirpalneInZoneFacade airpalneInZoneFacade;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message inMess = update.getMessage();

            if (hasCommand(inMess)) {
                commandFacade.provideAnsver(inMess);
            }

            if (inMess.hasLocation()) {
                airpalneInZoneFacade.showAircraftInZone(inMess);
            }

        }
        if (update.hasCallbackQuery()) {
            String callBackData = update.getCallbackQuery().getData();
            //TODO
            AircraftInfo aircraftInfo = aircraftInfoService.getAircraftInfos(callBackData);
            SendMessage message = prepareMessageService.formatAirplaneInfo(aircraftInfo, update.getCallbackQuery().getMessage());
            trySendMessage(message);

        }
    }

    private boolean hasCommand(Message message) {
        return message.hasEntities() && message.getEntities().stream()
                .anyMatch(messageEntity -> "bot_command".equals(messageEntity.getType()));
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    private void trySendMessage(SendMessage message) {
        try {
            sendMessage(message);
        } catch (TelegramApiException ex) {
            log.error("Problem with sending message: " + message, ex);
        }
    }

}
