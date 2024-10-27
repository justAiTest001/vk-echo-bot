package ru.justai.vkechobot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.justai.vkechobot.dto.CallbackRequest;
import ru.justai.vkechobot.processor.EventProcessor;

/**
 * Контроллер для обработки вебхуков от VK API.
 */
@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final EventProcessor eventProcessor;

    /**
     * Обрабатывает входящий запрос от VK API.
     *
     * @param callbackRequest запрос с данными события.
     * @return результат обработки события.
     */
    @PostMapping
    public String handleUpdate(@RequestBody CallbackRequest callbackRequest) {
        return eventProcessor.process(callbackRequest);
    }
}

