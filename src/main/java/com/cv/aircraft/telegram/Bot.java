package com.cv.aircraft.telegram;

import com.cv.aircraft.dto.Zone;
import com.cv.aircraft.service.DistanceService;
import com.vdurmont.emoji.EmojiParser;

import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import static java.util.Arrays.asList;

@Slf4j
public class Bot extends TelegramLongPollingBot {
    public static final String TOKEN = "391777415:AAGARqGctXzTAwTsVtLd_-qApvtj0i3AGTU";
    public static final String BOT_NAME = "K1evbot";

    private DistanceService distanceService;

    public Bot(ApplicationContext context) {
        this.distanceService = context.getBean(DistanceService.class);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message inMess = update.getMessage();
//
//            if (inMess.hasLocation()) {
//                showAircrafts(inMess);
//            }
//
//            SendMessage message = new SendMessage();
//            message.setChatId(inMess.getChatId());
//            message.setText("If for a long time you haven't answer - be sure that you turn on LOCALIZATION");
//            message.setReplyMarkup(locationButton("I see an aircraft!"));
//            trySendMessage(message);
            /**
             * https://apps.timwhitlock.info/emoji/tables/unicode#note3
             */
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            InlineKeyboardButton keyboardButton11 = null;
            String smile_emoji = EmojiParser.parseToUnicode(":airplane: some text");
                keyboardButton11 = new InlineKeyboardButton(smile_emoji);
            keyboardButton11.setCallbackData("data11");
            InlineKeyboardButton keyboardButton12 = new InlineKeyboardButton("\uD83D\uDCF7");
            keyboardButton12.setCallbackData("data12");
            InlineKeyboardButton keyboardButton21 = new InlineKeyboardButton("\uD83D\uDEEC");
            keyboardButton21.setCallbackData("data21");
            InlineKeyboardButton keyboardButton22 = new InlineKeyboardButton("\uD83D\uDC53 Are you see airplane ?");
            keyboardButton22.setCallbackData("data22");
            List<InlineKeyboardButton> firstLineBtn = asList(keyboardButton11, keyboardButton12);
            List<InlineKeyboardButton> secondLineBtn = asList(keyboardButton21, keyboardButton22);
            keyboardMarkup.setKeyboard(asList(firstLineBtn, secondLineBtn));
            SendMessage message = new SendMessage();
            message.setChatId(inMess.getChatId());
            message.setText("OLOLO");
            message.setReplyMarkup(keyboardMarkup);
            try {
                sendMessage(message);
            } catch (TelegramApiException e) {
                log.error("Ops",e);
            }
        }
        if (update.hasCallbackQuery()) {
            String id = update.getCallbackQuery().getId();
            String inlineMessageId = update.getCallbackQuery().getInlineMessageId();
            Message message1 = update.getCallbackQuery().getMessage();
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
            answerCallbackQuery.setShowAlert(false);
            answerCallbackQuery.setText("Ok, I'll show you airplanes");
            answerCallbackQuery.setCallbackQueryId(id);
            try {
                answerCallbackQuery(answerCallbackQuery);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
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
