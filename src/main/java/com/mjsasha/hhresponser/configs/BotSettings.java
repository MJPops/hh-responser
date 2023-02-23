package com.mjsasha.hhresponser.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "bot")
public class BotSettings {

    private String botToken;
    private String botName;
}
