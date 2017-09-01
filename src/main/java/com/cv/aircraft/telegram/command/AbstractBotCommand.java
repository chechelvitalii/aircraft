package com.cv.aircraft.telegram.command;

import com.cv.aircraft.util.SenderUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.commands.BotCommand;

public abstract class AbstractBotCommand extends BotCommand{
    @Autowired
    protected SenderUtil senderUtil;

    /**
     * Construct a command
     *
     * @param commandIdentifier the unique identifier of this command (e.g. the command string to enter into chat)
     * @param description       the description of this command
     */
    public AbstractBotCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }
}
