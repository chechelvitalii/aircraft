package com.cv.aircraft.telegram.command;

import com.cv.aircraft.util.SenderUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.AbsSender;

import static java.util.Arrays.asList;

@Component
public class StartCommand extends AbstractBotCommand {
    @Autowired
    private SenderUtil senderUtil;

    public StartCommand() {
        super("start", "Start application");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton geolocationButton = new KeyboardButton("Send you geolocation");
        geolocationButton.setRequestLocation(true);
        keyboardRow.add(geolocationButton);
        replyKeyboardMarkup.setKeyboard(asList(keyboardRow));
        senderUtil.sendKeyboard(chat.getId(), "ℹ Would you like get information about airplanes nearby you ❓", replyKeyboardMarkup);
    }
}
