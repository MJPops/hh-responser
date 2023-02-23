package com.mjsasha.hhresponser;

import com.mjsasha.hhresponser.configs.BotSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@EnableConfigurationProperties({
        BotSettings.class
})
public class HhResponserApplication {

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(HhResponserApplication.class, args);
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(app.getBean(Bot.class));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
