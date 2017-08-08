package com.cv.aircraft.service.telegram;

import com.cv.aircraft.dto.AirplaneShortInfo;
import com.cv.aircraft.dto.Zone;
import com.cv.aircraft.service.DistanceService;
import com.cv.aircraft.service.aircraft.AircraftIdService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

@Component
public class KeyboardAnswerProviderService {

    @Autowired
    private DistanceService distanceService;
    @Autowired
    private AircraftIdService aircraftIdService;

    public ReplyKeyboard makeKeyboard(Message message) {
        switch (message.getText()) {
            case "/start":
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setOneTimeKeyboard(true);
                replyKeyboardMarkup.setResizeKeyboard(true);
                KeyboardRow keyboardRow = new KeyboardRow();
                KeyboardButton geolocationButton = new KeyboardButton("Send you geolocation");
                geolocationButton.setRequestLocation(true);
                keyboardRow.add(geolocationButton);
                replyKeyboardMarkup.setKeyboard(asList(keyboardRow));
                return replyKeyboardMarkup;
            default:
                throw new IllegalArgumentException("Command not allowed !");
        }
    }

    //TODO nemes
    public ReplyKeyboard makeKeyboardWithAirplaneIds(Message message) {
        Float latitude = message.getLocation().getLatitude();
        Float longitude = message.getLocation().getLongitude();
        Zone zone = distanceService.computeZone(latitude, longitude);
        Set<AirplaneShortInfo> airplaneShortInfoInZone = aircraftIdService.getAirplaneShortInfoInZone(zone);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsButton = new ArrayList<>();
        for (AirplaneShortInfo airplaneShortInfo : airplaneShortInfoInZone) {
            InlineKeyboardButton inlineButton = new InlineKeyboardButton();
            inlineButton.setCallbackData(airplaneShortInfo.getId());
            inlineButton.setText(String.format("✈  %s -> %s", airplaneShortInfo.getFrom(), airplaneShortInfo.getTo()));
            rowsButton.add(asList(inlineButton));
        }
        inlineKeyboardMarkup.setKeyboard(rowsButton);
        return inlineKeyboardMarkup;
    }

}
