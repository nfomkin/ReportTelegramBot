package com.nfomkin.firsttelegrambot.bot;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

import com.nfomkin.firsttelegrambot.service.ResponseHandler;
import com.nfomkin.firsttelegrambot.service.ReportService;
import java.util.function.BiConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ReportBot extends AbilityBot {

    private final ResponseHandler responseHandler;
    private final ReportService reportService;
    @Value("${bot.creatorId}")
    private long creatorId;

    private ReportBot(@Value("${bot.token}") String botToken,
        @Value("${bot.name}") String botUsername,
        ReportService reportService) {
        super(botToken, botUsername);
        this.reportService = reportService;
        responseHandler = new ResponseHandler(silent, reportService, db);
    }

    public long creatorId() {
        return creatorId;
    }

    public Ability replyToStart() {
        return Ability
            .builder()
            .name("start")
            .info("create report")
            .locality(ALL)
            .privacy(PUBLIC)
            .action(ctx ->  responseHandler.replyToStart(ctx.chatId()))
            .build();
    }

    public Reply replyToButtons() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> responseHandler.replyToButtons(getChatId(upd), upd.getCallbackQuery().getData());
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }

    public Reply replyToText() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> responseHandler.replyToText(getChatId(upd), upd.getMessage().getText());
        return Reply.of(action, this::isTextAndNotCommand);
    }

    private boolean isTextAndNotCommand(Update upd) {
        return upd.hasMessage() && upd.getMessage().hasText() && !upd.getMessage().getText().contains("/");
    }

}






