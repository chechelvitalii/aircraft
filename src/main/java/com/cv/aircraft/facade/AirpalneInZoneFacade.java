package com.cv.aircraft.facade;

import com.cv.aircraft.dto.AirplaneShortInfo;
import com.cv.aircraft.dto.Zone;
import com.cv.aircraft.service.DistanceService;
import com.cv.aircraft.service.aircraft.AircraftIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.DefaultAbsSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

@Component
public class AirpalneInZoneFacade {
    @Autowired
    private DistanceService distanceService;
    @Autowired
    private AircraftIdService aircraftIdService;

    public void showAircraftInZone(Message message) {
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
//        return inlineKeyboardMarkup;

    }
}
