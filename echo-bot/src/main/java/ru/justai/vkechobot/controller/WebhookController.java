package ru.justai.vkechobot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.justai.vkechobot.dto.CallbackRequest;
import ru.justai.vkechobot.processor.CallbackRequestProcessor;

/**
 * Контроллер для обработки входящих запросов от VK API.
 * Маршрутизирует запросы на соответствующий процессор для обработки.
 */
@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {

    /**
     * Процессор для обработки входящих запросов.
     */
    private final CallbackRequestProcessor callbackRequestProcessor;

    /**
     * Обрабатывает входящее событие, передавая его процессору для дальнейшей обработки.
     *
     * @param callbackRequest объект запроса с данными события
     * @return результат обработки события
     */
    @PostMapping
    public String handleEvent(@RequestBody CallbackRequest callbackRequest) {
        return callbackRequestProcessor.process(callbackRequest);
    }
}
