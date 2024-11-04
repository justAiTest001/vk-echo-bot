package ru.justai.vkechobot.controller.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import ru.justai.vkechobot.exception.BotRuntimeException;

/**
 * Глобальный обработчик исключений для обработки ошибок в приложении.
 * Перехватывает исключения и возвращает понятные ответы клиенту.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Обработка кастомных исключений бота, наследуемых от BotRuntimeException.
     *
     * @param ex кастомное исключение бота.
     * @return ResponseEntity с информацией об ошибке.
     */
    @ExceptionHandler(BotRuntimeException.class)
    public ResponseEntity<String> handleBotRuntimeException(BotRuntimeException ex) {
        logger.error("Ошибка бота: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatusCode.valueOf(ex.getStatus()));
    }

    /**
     * Обработка исключений клиента HTTP-запросов (например, ошибки 4xx).
     *
     * @param ex исключение клиента HTTP-запроса.
     * @return ResponseEntity с информацией об ошибке.
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientError(HttpClientErrorException ex) {
        logger.error("Ошибка HTTP клиента: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("Ошибка HTTP клиента: " + ex.getStatusCode(), ex.getStatusCode());
    }

    /**
     * Обработка исключений сервера HTTP-запросов (например, ошибки 5xx).
     *
     * @param ex исключение сервера HTTP-запроса.
     * @return ResponseEntity с информацией об ошибке.
     */
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handleHttpServerError(HttpServerErrorException ex) {
        logger.error("Ошибка HTTP сервера: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("Ошибка HTTP сервера: " + ex.getStatusCode(), ex.getStatusCode());
    }

    /**
     * Общая обработка всех остальных исключений.
     *
     * @param ex общее исключение.
     * @return ResponseEntity с информацией об ошибке.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        logger.error("Произошла непредвиденная ошибка: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("Произошла непредвиденная ошибка.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
