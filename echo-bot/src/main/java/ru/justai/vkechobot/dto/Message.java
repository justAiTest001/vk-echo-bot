package ru.justai.vkechobot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Модель сообщения, получаемого от VK API.
 * Содержит идентификатор отправителя и текст сообщения.
 */
@Setter
@Getter
public class Message {

    /**
     * Идентификатор отправителя сообщения.
     */
    @JsonProperty("peer_id")
    private Long peerId;

    /**
     * Текст сообщения.
     */
    @JsonProperty("text")
    private String text;
}

