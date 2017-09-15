package com.cv.aircraft.telegram.handler;

import com.cv.aircraft.facade.AirplaneInTargetAreaFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationHandler implements Handler {
    @Autowired
    private AirplaneInTargetAreaFacade airplaneInTargetAreaFacade;

    @Override
    public void execute(org.telegram.telegrambots.api.objects.Message inputMess) {
        airplaneInTargetAreaFacade.showAircraftInTargetArea(inputMess);
    }
}
