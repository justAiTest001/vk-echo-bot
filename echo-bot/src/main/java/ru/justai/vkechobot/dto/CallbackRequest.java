package ru.justai.vkechobot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Модель запроса обратного вызова (callback) от VK API.
 * Содержит данные о типе события, объекте события и идентификаторе группы.
 */
@Setter
@Getter
public class CallbackRequest {

    /**
     * Тип события, отправленного VK API.
     */
    private String type;

    /**
     * Объект события, содержащий данные, специфичные для типа события.
     */
    private Object object;

    /**
     * Идентификатор группы, для которой отправлен запрос.
     */
    @JsonProperty("group_id")
    private Long groupId;
}

