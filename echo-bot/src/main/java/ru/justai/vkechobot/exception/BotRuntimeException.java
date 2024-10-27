package ru.justai.vkechobot.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Базовый класс для всех исключений, связанных с ботом.
 * Содержит HTTP-статус и сообщение об ошибке.
 */
@Getter
public class BotRuntimeException extends RuntimeException {

    private final HttpStatus status;

    private final String message;

    /**
     * Создаёт исключение с указанным HTTP-статусом и сообщением.
     *
     * @param status HTTP-статус ошибки.
     * @param message сообщение об ошибке.
     */
    public BotRuntimeException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    /**
     * Создаёт исключение с указанным HTTP-статусом, сообщением и исходной причиной.
     *
     * @param status HTTP-статус ошибки.
     * @param message сообщение об ошибке.
     * @param cause исходная причина исключения.
     */
    public BotRuntimeException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.message = message;
    }
}


