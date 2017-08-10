package com.cv.aircraft.util;

import com.cv.aircraft.telegram.Bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class SenderUtil {
    @Autowired
    private Bot bot;

    public void sendKeyboard(Message inMess, String responseText, ReplyKeyboard keyboard) {
        SendMessage sendMessage = getSendMessage(inMess, responseText);
        sendMessage.setReplyMarkup(keyboard);
        trySend(sendMessage);
    }

    public void sendText(Message inMess, String responseText) {
        SendMessage sendMessage = getSendMessage(inMess, responseText);
        trySend(sendMessage);
    }

    private SendMessage getSendMessage(Message inMess, String responseText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(inMess.getChatId());
        sendMessage.setText(responseText);
        return sendMessage;
    }

    private void trySend(SendMessage sendMessage) {
        try {
            bot.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
