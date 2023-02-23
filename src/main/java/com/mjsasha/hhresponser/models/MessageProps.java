package com.mjsasha.hhresponser.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageProps {

    private Long chatId;
    private String userName;
    private String text;
}
