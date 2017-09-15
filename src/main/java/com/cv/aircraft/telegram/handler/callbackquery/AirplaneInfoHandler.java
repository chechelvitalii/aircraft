package com.cv.aircraft.telegram.handler.callbackquery;

import com.cv.aircraft.dto.AircraftInfo;
import com.cv.aircraft.service.AircraftInfoService;
import com.cv.aircraft.telegram.handler.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.objects.CallbackQuery;

import static com.cv.aircraft.util.MessageBuilder.buildAirplaneInfoMessage;

@Component
public class AirplaneInfoHandler extends Handler<CallbackQuery> {
    @Autowired
    private AircraftInfoService aircraftInfoService;

    @Override
    public boolean match(BotApiObject botApiObject) {
        return botApiObject instanceof CallbackQuery;
    }

    @Override
    protected void process(CallbackQuery callbackQuery) {
        String airplaneId = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();
        AircraftInfo aircraftInfo = aircraftInfoService.getAircraftInfos(airplaneId);
        String message = buildAirplaneInfoMessage(aircraftInfo);
        senderUtil.sendText(chatId, message);
    }
}
