package com.cv.aircraft.telegram.handler;

public interface Handler {
    void execute(org.telegram.telegrambots.api.objects.Message inputMess);
}
