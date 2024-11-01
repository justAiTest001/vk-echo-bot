package ru.justai.vkechobot.exception.handler;

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
 * Перехватывает исключения и возвращает клиенту понятные ответы.
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
        logger.error("Bot error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatusCode.valueOf(ex.getStatus()));
    }

    /**
     * Обработка исключений клиента HTTP-запросов (например, 4xx ошибки).
     *
     * @param ex исключение клиента HTTP-запроса.
     * @return ResponseEntity с информацией об ошибке.
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientError(HttpClientErrorException ex) {
        logger.error("HTTP Client Error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("HTTP Client Error: " + ex.getStatusCode(), ex.getStatusCode());
    }

    /**
     * Обработка исключений сервера HTTP-запросов (например, 5xx ошибки).
     *
     * @param ex исключение сервера HTTP-запроса.
     * @return ResponseEntity с информацией об ошибке.
     */
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handleHttpServerError(HttpServerErrorException ex) {
        logger.error("HTTP Server Error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("HTTP Server Error: " + ex.getStatusCode(), ex.getStatusCode());
    }

    /**
     * Общая обработка всех остальных исключений.
     *
     * @param ex общее исключение.
     * @return ResponseEntity с информацией об ошибке.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

