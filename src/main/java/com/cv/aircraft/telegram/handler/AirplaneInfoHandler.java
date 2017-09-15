package com.cv.aircraft.telegram.handler;

import com.cv.aircraft.dto.AircraftInfo;
import com.cv.aircraft.service.aircraft.AircraftInfoService;
import com.cv.aircraft.service.telegram.PrepareMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.CallbackQuery;

@Component
public class AirplaneInfoHandler extends AbstractCallbackHandler {
    @Autowired
    private PrepareMessageService prepareMessageService;
    @Autowired
    private AircraftInfoService aircraftInfoService;

    @Override
    public void execute(CallbackQuery callbackQuery) {
        String callBackData = callbackQuery.getData();
//            //TODO
        AircraftInfo aircraftInfo = aircraftInfoService.getAircraftInfos(callBackData);
        String message = prepareMessageService.formatAirplaneInfo(aircraftInfo);
        senderUtil.sendText(callbackQuery.getMessage().getChatId(),message);
    }
}
