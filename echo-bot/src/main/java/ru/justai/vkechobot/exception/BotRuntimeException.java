package ru.justai.vkechobot.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Базовый класс для всех исключений, связанных с ботом.
 * Содержит HTTP-статус и сообщение об ошибке.
 */
@Getter
public class BotRuntimeException extends RuntimeException {

    /**
     * HTTP-статус, связанный с исключением.
     */
    private final int status;

    /**
     * Сообщение об ошибке, связанное с исключением.
     */
    private final String message;

    /**
     * Конструктор, принимающий HTTP-статус и сообщение об ошибке.
     *
     * @param status HTTP-статус исключения.
     * @param message сообщение об ошибке.
     */
    public BotRuntimeException(HttpStatus status, String message) {
        super(message);
        this.status = status.value();
        this.message = message;
    }

    /**
     * Конструктор, принимающий числовой статус и сообщение об ошибке.
     *
     * @param status числовое значение статуса исключения.
     * @param message сообщение об ошибке.
     */
    public BotRuntimeException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    /**
     * Конструктор, принимающий HTTP-статус, сообщение об ошибке и причину исключения.
     *
     * @param status HTTP-статус исключения.
     * @param message сообщение об ошибке.
     * @param cause исходная причина исключения.
     */
    public BotRuntimeException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status.value();
        this.message = message;
    }
}
