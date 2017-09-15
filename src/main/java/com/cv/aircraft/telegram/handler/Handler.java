package com.cv.aircraft.telegram.handler;

import com.cv.aircraft.util.SenderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.interfaces.BotApiObject;

public abstract class Handler<T extends BotApiObject> {
    @Autowired
    protected SenderUtil senderUtil;

    protected abstract boolean match(BotApiObject botApiObject);

    public final void execute(BotApiObject botApiObject) {
        if (match(botApiObject)) {
            process((T) botApiObject);
        }
    }

    protected abstract void process(T botApiObject);
}