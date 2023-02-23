package com.mjsasha.hhresponser;

import com.mjsasha.hhresponser.configs.BotSettings;
import com.mjsasha.hhresponser.handlers.SimpleMessageHandler;
import com.mjsasha.hhresponser.models.MessageProps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public final class Bot extends TelegramLongPollingCommandBot {

    private static String BOT_NAME;
    private static String BOT_TOKEN;
    private final SimpleMessageHandler simpleMessageHandler;

    public Bot(BotSettings botSettings, SimpleMessageHandler simpleMessageHandler) {
        super();
        BOT_NAME = botSettings.getBotName();
        BOT_TOKEN = botSettings.getBotToken();

        log.info(String.format("### Bot started\n### Name:%s\n### Token: %s\n", BOT_NAME, BOT_TOKEN));

        this.simpleMessageHandler = simpleMessageHandler;
        register(new HelpCommand("help", "Помощь", "some Desc"));
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        var messageProps = new MessageProps(
                message.getChatId(),
                getUserName(message),
                message.getText()
        );

        String answer = simpleMessageHandler.distributeMessage(messageProps);
        setAnswer(messageProps, answer);
    }

    private String getUserName(Message message) {
        User user = message.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    private void setAnswer(MessageProps messageProps, String answerText) {
        SendMessage answer = new SendMessage();
        answer.setText(answerText);
        answer.setChatId(messageProps.getChatId().toString());
        try {
            execute(answer);
        } catch (TelegramApiException ex) {
            log.error("Can't answer to user " + messageProps.getUserName(), ex);
        }
    }
}
