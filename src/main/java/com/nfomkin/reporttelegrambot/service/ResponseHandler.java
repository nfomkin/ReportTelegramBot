package com.nfomkin.firsttelegrambot.service;

import com.nfomkin.firsttelegrambot.domain.Constants;
import com.nfomkin.firsttelegrambot.domain.Report;
import com.nfomkin.firsttelegrambot.domain.State;
import com.nfomkin.firsttelegrambot.service.KeyboardFactory;
import com.nfomkin.firsttelegrambot.service.ReportService;
import java.time.LocalDate;
import java.util.Map;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ResponseHandler {

    private final SilentSender sender;
    private final ReportService service;
    private final Map<Long, State> chatStates;
    private final Map<Long, Report> chatReports;

    public ResponseHandler(SilentSender sender, ReportService service,
        DBContext db) {
        this.sender = sender;
        this.service = service;
        chatStates = db.getMap("chatStates");
        chatReports = db.getMap("chatReports");
    }

    public void replyToStart(Long chatId) {
        sender.send(Constants.START_REPLY, chatId);
        sender.execute(SendMessage.builder()
                .text(Constants.INSTRUCTION)
                .chatId(String.valueOf(chatId))
                .replyMarkup(KeyboardFactory.withUnderstandButton())
            .build());
    }

    public void replyToButtons(Long chatId, String data) {
        switch (data) {
            case Constants.UNDERSTAND:
                replyToUnderstand(chatId);
                break;
            case Constants.DATE:
                replyToDate(chatId);
                break;
        }
    }

    private void replyToUnderstand(Long chatId) {
        sender.execute(SendMessage.builder()
                .text(Constants.CHOICE_MODE)
                .chatId(String.valueOf(chatId))
                .replyMarkup(KeyboardFactory.withChoiceModeButtons())
            .build());
    }

    private void replyToDate(Long chatId) {
        // т.к создание отчета может начаться с любого шага, проверяем создан ли отчет
        // если нет - создаем
        if (!chatReports.containsKey(chatId)) {
            try {
                chatReports.put(chatId, new Report());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        sender.execute(SendMessage.builder()
                .text("Введите, пожалуйста, дату: ")
                .chatId(String.valueOf(chatId))
            .build());
        chatStates.put(chatId, State.DATE);
    }

    public void replyToText(Long chatId, String message) {
        State chatState = chatStates.get(chatId);
        switch (chatState) {
            case DATE:
                checkDate(chatId, message);
                break;
            case CODE:
                checkCode(chatId, message);
                break;
            case ROUTE_NUMBER:
                checkRouteNumber(chatId, message);
                break;
            case DESCRIPTION:
                checkDescription(chatId, message);
                break;
        }
    }

    private void checkDescription(Long chatId, String message) {

    }

    private void checkRouteNumber(Long chatId, String message) {

    }

    private void checkCode(Long chatId, String message) {

    }

    private void checkDate(Long chatId, String message) {
        if (service.isDate(message)) {
            Report chatReport = chatReports.get(chatId);
            chatReport.setDate(LocalDate.parse(message));
            chatReports.put(chatId, chatReport);
            sender.execute(SendMessage.builder()
                    .text("Что дальше?")
                    .chatId(String.valueOf(chatId))
                    .replyMarkup(KeyboardFactory.withChoiceModeButtons())
                .build());
        }
        else {
            sender.send("Попробуйте еще раз: ", chatId);
        }
    }
}
