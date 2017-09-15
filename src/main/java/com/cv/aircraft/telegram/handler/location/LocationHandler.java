package com.cv.aircraft.telegram.handler.location;

import com.cv.aircraft.dto.AirplaneShortInfo;
import com.cv.aircraft.dto.TargetArea;
import com.cv.aircraft.service.AreaService;
import com.cv.aircraft.service.AircraftIdService;
import com.cv.aircraft.service.AirportService;
import com.cv.aircraft.telegram.handler.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

@Component
public class LocationHandler extends Handler<Message> {
    @Autowired
    private AreaService areaService;
    @Autowired
    private AircraftIdService aircraftIdService;
    @Autowired
    private AirportService airportService;

    @Override
    public boolean match(BotApiObject botApiObject) {
        return botApiObject instanceof Message;
    }

    @Override
    protected void process(Message inputMess) {
        Float latitude = inputMess.getLocation().getLatitude();
        Float longitude = inputMess.getLocation().getLongitude();
        Long chatId = inputMess.getChatId();

        TargetArea targetArea = areaService.determinateTargetArea(latitude, longitude);
        Set<AirplaneShortInfo> airplaneShortInfoInZone = aircraftIdService.getAirplaneShortInfoInZone(targetArea);

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
        senderUtil.sendKeyboard(chatId, "Please, choose one airplane.", inlineKeyboardMarkup);
    }
}
