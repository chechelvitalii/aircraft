package com.cv.aircraft.facade;

import com.cv.aircraft.util.SenderUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import static java.util.Arrays.asList;

@Component
public class CommandFacade {
    @Autowired
    private SenderUtil senderUtil;

    public void provideAnsver(Message inMess) {
        switch (inMess.getText()) {
            case "/start":
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setOneTimeKeyboard(true);
                replyKeyboardMarkup.setResizeKeyboard(true);
                KeyboardRow keyboardRow = new KeyboardRow();
                KeyboardButton geolocationButton = new KeyboardButton("Send you geolocation");
                geolocationButton.setRequestLocation(true);
                keyboardRow.add(geolocationButton);
                replyKeyboardMarkup.setKeyboard(asList(keyboardRow));
                senderUtil.sendKeyboard(inMess, "ℹ Would you like get information about airplanes nearby you ❓", replyKeyboardMarkup);
            default:
                throw new IllegalArgumentException("Command not allowed !");
        }
    }
}
