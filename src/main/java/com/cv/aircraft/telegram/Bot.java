package com.cv.aircraft.telegram;

import com.cv.aircraft.dto.AircraftInfo;
import com.cv.aircraft.service.aircraft.AircraftInfoService;
import com.cv.aircraft.service.telegram.KeyboardAnswerProviderService;
import com.cv.aircraft.service.telegram.PrepareMessageService;

import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bot extends TelegramLongPollingBot {
    public static final String TOKEN = "391777415:AAGARqGctXzTAwTsVtLd_-qApvtj0i3AGTU";
    public static final String BOT_NAME = "K1evbot";

    private KeyboardAnswerProviderService keyboardAnswerProviderService;
    private PrepareMessageService prepareMessageService;
    private AircraftInfoService aircraftInfoService;

    public Bot(ApplicationContext context) {
        this.keyboardAnswerProviderService = context.getBean(KeyboardAnswerProviderService.class);
        this.prepareMessageService = context.getBean(PrepareMessageService.class);
        this.aircraftInfoService = context.getBean(AircraftInfoService.class);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message inMess = update.getMessage();

            if (hasCommand(inMess)) {
                ReplyKeyboard keyboard = keyboardAnswerProviderService.makeKeyboard(update.getMessage());
                SendMessage sendMessage = prepareMessageService.makeMessageWithKeyBoard(keyboard, "ℹ Would you like get information about airplanes nearby you ❓", inMess);
                trySendMessage(sendMessage);
            }

            if (inMess.hasLocation()) {
                ReplyKeyboard keyboard = keyboardAnswerProviderService.makeKeyboardWithAirplaneIds(inMess);
                SendMessage sendMessage = prepareMessageService.makeMessageWithKeyBoard(keyboard, "Please, choose one airplane.", inMess);
                trySendMessage(sendMessage);
            }

//            SendMessage message = new SendMessage();
//            message.setChatId(inMess.getChatId());
//            message.setText("If for a long time you haven't answer - be sure that you turn on LOCALIZATION");
//            if (inMess.getText().equals("Hi")) {
//
////            message.setReplyMarkup(locationButton("I see an aircraft!"));
//                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//                replyKeyboardMarkup.setResizeKeyboard(true);
//                KeyboardRow keyboardButtons = new KeyboardRow();
//                KeyboardButton button = new KeyboardButton();
//                button.setText("button_1");
//                keyboardButtons.add(0, button);
//                replyKeyboardMarkup.setKeyboard(asList(keyboardButtons));
//                message.setReplyMarkup(replyKeyboardMarkup);
//                trySendMessage(message);
//            } else if (inMess.getText().equals("Yo")) {
//                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
//                inlineKeyboardButton.setText("ololo_text");
//                inlineKeyboardButton.setCallbackData("yo data");
//                inlineKeyboardMarkup.setKeyboard(asList(asList(inlineKeyboardButton)));
//                message.setReplyMarkup(inlineKeyboardMarkup);
//                trySendMessage(message);
//            } else if (inMess.getText().equals("Next")) {
//                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//                replyKeyboardMarkup.setOneTimeKeyboard(true);
//                KeyboardRow keyboardButtons = new KeyboardRow();
//                keyboardButtons.add("next >>");
//                replyKeyboardMarkup.setKeyboard(asList(keyboardButtons));
//                message.setReplyMarkup(replyKeyboardMarkup);
//                trySendMessage(message);
//            }
        }
        if (update.hasCallbackQuery()) {
            String callBackData = update.getCallbackQuery().getData();
            //TODO
            AircraftInfo aircraftInfo = aircraftInfoService.getAircraftInfos(callBackData);
            SendMessage message = prepareMessageService.formatAirplaneInfo(aircraftInfo, update.getCallbackQuery().getMessage());
            trySendMessage(message);

        }
//            }
//            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
//            inlineKeyboardButton.setText(callBackData);
//            inlineKeyboardButton.setCallbackData("http://google.com");
//            inlineKeyboardMarkup.setKeyboard(asList(asList(inlineKeyboardButton)));
//            SendMessage message = new SendMessage();
//            message.setText("data came");
//            message.setChatId(update.getCallbackQuery().getMessage().getChatId());
//            message.setReplyMarkup(inlineKeyboardMarkup);
//            trySendMessage(message);
    }

    private boolean hasCommand(Message message) {
        return message.hasEntities() && message.getEntities().stream()
                .anyMatch(messageEntity -> "bot_command".equals(messageEntity.getType()));
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
        } catch (TelegramApiException ex) {
            log.error("Problem with sending message: " + message, ex);
        }
    }

}
