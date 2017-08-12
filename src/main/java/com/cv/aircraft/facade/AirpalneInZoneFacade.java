package com.cv.aircraft.facade;

import com.cv.aircraft.dto.AirplaneShortInfo;
import com.cv.aircraft.dto.Zone;
import com.cv.aircraft.repository.AirportRepository;
import com.cv.aircraft.service.DistanceService;
import com.cv.aircraft.service.aircraft.AircraftIdService;
import com.cv.aircraft.service.aircraft.AirportService;
import com.cv.aircraft.util.SenderUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

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
    @Autowired
    private SenderUtil senderUtil;
    @Autowired
    private AirportService airportService;

    public void showAircraftInZone(Message inMess) {
        Float latitude = inMess.getLocation().getLatitude();
        Float longitude = inMess.getLocation().getLongitude();
        Zone zone = distanceService.computeZone(latitude, longitude);
        Set<AirplaneShortInfo> airplaneShortInfoInZone = aircraftIdService.getAirplaneShortInfoInZone(zone);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsButton = new ArrayList<>();
        for (AirplaneShortInfo airplaneShortInfo : airplaneShortInfoInZone) {
            InlineKeyboardButton inlineButton = new InlineKeyboardButton();
            inlineButton.setCallbackData(airplaneShortInfo.getId());
            String from = airportService.getCityAndCountryNameByIataCode(airplaneShortInfo.getFrom());
            String to = airportService.getCityAndCountryNameByIataCode(airplaneShortInfo.getTo());
            inlineButton.setText(String.format("✈  %s -> %s", from, to));
            rowsButton.add(asList(inlineButton));
        }
        inlineKeyboardMarkup.setKeyboard(rowsButton);
        senderUtil.sendKeyboard(inMess, "Please, choose one airplane.", inlineKeyboardMarkup);
    }
}
