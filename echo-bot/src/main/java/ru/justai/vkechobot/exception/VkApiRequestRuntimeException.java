package ru.justai.vkechobot.exception;

import ru.justai.vkechobot.dto.VkApiError;

/**
 * Исключение, выбрасываемое при ошибке выполнения запроса к VK API.
 * Содержит код ошибки и сообщение, полученные из объекта {@link VkApiError}.
 */
public class VkApiRequestRuntimeException extends BotRuntimeException {

    /**
     * Конструктор исключения, принимающий объект ошибки VK API.
     *
     * @param e объект {@link VkApiError}, содержащий информацию об ошибке VK API.
     */
    public VkApiRequestRuntimeException(VkApiError e) {
        super(e.getCode(), e.getMessage());
    }
}

