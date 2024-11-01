package ru.justai.vkechobot.exception;

import ru.justai.vkechobot.dto.VkApiError;

/**
 * Исключение, выбрасываемое при ошибке выполнения запроса к VK API.
 */
public class VkApiRequestRuntimeException extends BotRuntimeException {


    public VkApiRequestRuntimeException(VkApiError e) {
        super(e.getCode(), e.getMessage());
    }
}
