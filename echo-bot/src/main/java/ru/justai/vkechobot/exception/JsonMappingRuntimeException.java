package ru.justai.vkechobot.exception;

import org.springframework.http.HttpStatus;

/**
 * Исключение, выбрасываемое при ошибке преобразования JSON-узла в объект.
 */
public class JsonMappingRuntimeException extends BotRuntimeException {

    /**
     * Создаёт исключение для ошибки маппинга JSON с исходной причиной.
     *
     * @param e причина исключения.
     */
    public JsonMappingRuntimeException(Throwable e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to map JSON node to object: " + e.getMessage(), e);
    }
}
