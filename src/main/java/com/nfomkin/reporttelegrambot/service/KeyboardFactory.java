package com.nfomkin.firsttelegrambot.service;

import com.nfomkin.firsttelegrambot.domain.Constants;
import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class KeyboardFactory {

    public static ReplyKeyboard withUnderstandButton() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(InlineKeyboardButton.builder()
            .text(Constants.UNDERSTAND)
            .callbackData(Constants.UNDERSTAND)
            .build());
        rowsInline.add(rowInline);
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static ReplyKeyboard withChoiceModeButtons() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        rowInline1.add(InlineKeyboardButton.builder()
            .text(Constants.DATE)
            .callbackData(Constants.DATE)
            .build());
        rowInline1.add(InlineKeyboardButton.builder()
            .text(Constants.CODE)
            .callbackData(Constants.CODE)
            .build());
        rowInline1.add(InlineKeyboardButton.builder()
            .text(Constants.ROUTE_NUMBER)
            .callbackData(Constants.ROUTE_NUMBER)
            .build());
        rowInline1.add(InlineKeyboardButton.builder()
            .text(Constants.PHOTOS)
            .callbackData(Constants.PHOTOS)
            .build());
        rowInline2.add(InlineKeyboardButton.builder()
            .text(Constants.DESCRIPTION)
            .callbackData(Constants.DESCRIPTION_CALLBACK)
            .build());
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }
}
