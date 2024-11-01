package ru.justai.vkechobot.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Базовый класс для всех исключений, связанных с ботом.
 * Содержит HTTP-статус и сообщение об ошибке.
 */
@Getter
public class BotRuntimeException extends RuntimeException {

    private final int status;

    private final String message;

    public BotRuntimeException(HttpStatus status, String message) {
        super(message);
        this.status = status.value();
        this.message = message;
    }

    public BotRuntimeException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public BotRuntimeException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status.value();
        this.message = message;
    }
}


