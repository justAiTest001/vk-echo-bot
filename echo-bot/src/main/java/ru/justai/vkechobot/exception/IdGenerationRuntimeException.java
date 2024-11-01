package ru.justai.vkechobot.exception;

import org.springframework.http.HttpStatus;

/**
 * Исключение, возникающее при ошибке генерации уникального идентификатора.
 * Расширяет {@link BotRuntimeException} и используется для обработки ошибок генерации идентификаторов.
 */
public class IdGenerationRuntimeException extends BotRuntimeException {

    /**
     * Создает новое исключение с сообщением об ошибке и причиной.
     *
     * @param e исходное исключение, вызвавшее ошибку генерации идентификатора
     */
    public IdGenerationRuntimeException(Throwable e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Error during ID generation: " + e.getMessage(), e);
    }
}

