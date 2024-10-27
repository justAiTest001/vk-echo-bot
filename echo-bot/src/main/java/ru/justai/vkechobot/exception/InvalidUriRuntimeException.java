package ru.justai.vkechobot.exception;

import org.springframework.http.HttpStatus;

/**
 * Исключение, выбрасываемое при передаче недопустимого URI.
 */
public class InvalidUriRuntimeException extends BotRuntimeException {

    /**
     * Создаёт исключение для недопустимого URI с исходной причиной.
     *
     * @param uri строковое представление недопустимого URI.
     * @param e причина исключения.
     */
    public InvalidUriRuntimeException(String uri, Throwable e) {
        super(HttpStatus.BAD_REQUEST, "Invalid URI: " + uri, e);
    }
}

