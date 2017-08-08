package com.cv.aircraft.service.telegram;

import com.cv.aircraft.dto.AircraftInfo;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class PrepareMessageService {
    public SendMessage formatAirplaneInfo(AircraftInfo aircraftInfo, Message message) {
        String format = String.format("Model: %s \n Speed: %s", aircraftInfo.getModel(), aircraftInfo.getSpeed());
        return getSendMessage(message, format);
    }

    public SendMessage makeMessageWithKeyBoard(ReplyKeyboard keyboard, String responseMess, Message inMess) {
        SendMessage sendMessage = getSendMessage(inMess, responseMess);
        sendMessage.setReplyMarkup(keyboard);
        return sendMessage;
    }

    private SendMessage getSendMessage(Message message, String responseMess) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(responseMess);
        return sendMessage;
    }
}
