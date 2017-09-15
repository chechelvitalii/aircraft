package com.cv.aircraft.telegram.handler;

public interface MessageHandler {
    void execute(org.telegram.telegrambots.api.objects.Message inputMess);
}
