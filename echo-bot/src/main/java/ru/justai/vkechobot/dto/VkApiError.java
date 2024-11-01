package ru.justai.vkechobot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VkApiError {

    @JsonProperty("error_code")
    private int code;

    @JsonProperty("error_msg")
    private String message;
}