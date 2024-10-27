package ru.justai.vkechobot.exception;

import org.springframework.http.HttpStatus;

/**
 * Исключение, выбрасываемое при ошибке выполнения запроса к VK API.
 */
public class VkApiRequestRuntimeException extends BotRuntimeException {

    /**
     * Создаёт исключение для ошибки запроса к VK API с указанным HTTP-статусом.
     *
     * @param status HTTP-статус, связанный с ошибкой запроса.
     */
    public VkApiRequestRuntimeException(HttpStatus status) {
        super(status, "VK API request failed");
    }
}
