package com.cv.aircraft.telegram.handler;

import com.cv.aircraft.util.SenderUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.objects.CallbackQuery;

public abstract class AbstractCallbackHandler {
    @Autowired
    protected SenderUtil senderUtil;

    public abstract void execute(CallbackQuery callbackQuery);

}
