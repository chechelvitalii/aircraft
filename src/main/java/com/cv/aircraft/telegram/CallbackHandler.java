package com.cv.aircraft.telegram;

import com.cv.aircraft.telegram.handler.Handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;

@Component
public class CallbackHandler implements Handler {
    @Override
    public void execute(Message inputMess) {

    }
}
