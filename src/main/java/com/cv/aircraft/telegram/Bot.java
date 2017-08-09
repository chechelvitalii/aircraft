package com.cv.aircraft.telegram;

import com.cv.aircraft.dto.AircraftInfo;
import com.cv.aircraft.service.aircraft.AircraftInfoService;
import com.cv.aircraft.service.telegram.KeyboardAnswerProviderService;
import com.cv.aircraft.service.telegram.PrepareMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Slf4j
@Component
@Lazy
public class Bot extends TelegramLongPollingBot {
    public static final String TOKEN = "391777415:AAGARqGctXzTAwTsVtLd_-qApvtj0i3AGTU";
    public static final String BOT_NAME = "K1evbot";

    @Autowired
    private KeyboardAnswerProviderService keyboardAnswerProviderService;
    @Autowired
    private PrepareMessageService prepareMessageService;
    @Autowired
    private AircraftInfoService aircraftInfoService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message inMess = update.getMessage();

            if (hasCommand(inMess)) {
                ReplyKeyboard keyboard = keyboardAnswerProviderService.makeKeyboard(update.getMessage());
                SendMessage sendMessage = prepareMessageService.makeMessageWithKeyBoard(keyboard, "ℹ Would you like get information about airplanes nearby you ❓", inMess);
                trySendMessage(sendMessage);
            }

            if (inMess.hasLocation()) {
                ReplyKeyboard keyboard = keyboardAnswerProviderService.makeKeyboardWithAirplaneIds(inMess);
                SendMessage sendMessage = prepareMessageService.makeMessageWithKeyBoard(keyboard, "Please, choose one airplane.", inMess);
                trySendMessage(sendMessage);
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
