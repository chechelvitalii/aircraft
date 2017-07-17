package com.cv.aircraft.telegram;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import static java.util.Arrays.asList;

public class Button {

    public static ReplyKeyboardMarkup locationButton(String labele) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setOneTimeKeyboard(true);
        KeyboardButton locationButn = new KeyboardButton(labele);
        locationButn.setRequestLocation(true);
        KeyboardRow keyboardButtons = new KeyboardRow();
        keyboardButtons.add(locationButn);
        keyboardMarkup.setKeyboard(asList(keyboardButtons));
        return keyboardMarkup;
    }

}
