package ru.justai.vkechobot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс, представляющий ошибку VK API.
 * Содержит код ошибки и сообщение об ошибке, возвращаемые VK API.
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VkApiError {

    /**
     * Код ошибки, возвращаемый VK API.
     */
    @JsonProperty("error_code")
    private int code;

    /**
     * Сообщение об ошибке, возвращаемое VK API.
     */
    @JsonProperty("error_msg")
    private String message;
}
