package com.cv.aircraft.service.telegram;

import com.cv.aircraft.dto.AircraftInfo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class PrepareMessageService {
    public SendMessage formatAirplaneInfo(AircraftInfo aircraftInfo, Message message) {
        String template = "Model: %s\nAirline: %s\nDepartureCountry: %s\nDepartureCity: %s\nArrivalCountry: %s\nArrivalCity: %s\nSpeed: %s\nHeight: %s\nimg: %s";

        String format = String.format(template,
                aircraftInfo.getModel(), aircraftInfo.getAirline(),
                aircraftInfo.getDepartureCountry(), aircraftInfo.getDepartureCity(),
                aircraftInfo.getArrivalCountry(), aircraftInfo.getArrivalCity(),
                aircraftInfo.getSpeed(), aircraftInfo.getHeight(),
                aircraftInfo.getImg());
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
