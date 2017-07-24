package com.cv.aircraft.telegram;

import com.cv.aircraft.dto.Zone;
import com.cv.aircraft.service.DistanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static com.cv.aircraft.telegram.Button.locationButton;

@Slf4j
public class Bot extends TelegramLongPollingBot {
    public static final String TOKEN = "391777415:AAGSrUbXMaCFqVyeR6v5fyFDqIQnVab3-Jc";
    public static final String BOT_NAME = "K1evbot";

    private DistanceService distanceService;

    public Bot(ApplicationContext context) {
        this.distanceService = context.getBean(DistanceService.class);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message inMess = update.getMessage();

            if (inMess.hasLocation()) {
                showAircrafts(inMess);
            }

            SendMessage message = new SendMessage();
            message.setChatId(inMess.getChatId());
            message.setText("If for a long time you haven't answer - be sure that you turn on LOCALIZATION");
            message.setReplyMarkup(locationButton("I see an aircraft!"));
            trySendMessage(message);

        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    private void trySendMessage(SendMessage message) {
        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            log.error("Problem with sending message: " + message);
        }
    }

    private void showAircrafts(Message message) {
        Float latitude = message.getLocation().getLatitude();
        Float longitude = message.getLocation().getLongitude();
        Zone computeZone = distanceService.computeZone(latitude, longitude);
    }
}
