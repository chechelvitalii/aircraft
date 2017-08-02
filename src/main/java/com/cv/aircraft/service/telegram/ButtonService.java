package com.cv.aircraft.service.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import static java.util.Arrays.asList;

@Component
public class ButtonService {

    public ReplyKeyboardMarkup getLocationKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(true);
        KeyboardButton locationBtn = new KeyboardButton("I see an airplane!");
        locationBtn.setRequestLocation(true);
        KeyboardRow keyboardButtons = new KeyboardRow();
        keyboardButtons.add(locationBtn);
        keyboard.setKeyboard(asList(keyboardButtons));
        return keyboard;
    }
}
