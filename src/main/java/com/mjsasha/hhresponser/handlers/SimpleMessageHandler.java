package com.mjsasha.hhresponser.handlers;

import com.mjsasha.hhresponser.models.MessageProps;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class SimpleMessageHandler {

    private final Map<String, Function<MessageProps, String>> responsesForMessages;

    public SimpleMessageHandler() {
        this.responsesForMessages = Map.of(
                "Hello", this::sayHello
        );
    }

    public String distributeMessage(MessageProps messageProps) {
        return responsesForMessages.getOrDefault(messageProps.getText(), mp -> "I can't response to this message")
                .apply(messageProps);
    }

    private String sayHello(MessageProps messageProps){
        return "Hello " + messageProps.getUserName();
    }
}
