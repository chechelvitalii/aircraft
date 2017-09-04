package com.cv.aircraft.telegram.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

@Component
public class VersionCommand extends AbstractBotCommand {
    @Value("${build.timestamp}")
    private String buildTimestampVersion;


    public VersionCommand() {
        super("version", "get application version");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        senderUtil.sendText(chat.getId(), buildTimestampVersion);
    }
}
