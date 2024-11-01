package ru.justai.vkechobot.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.justai.vkechobot.dto.CallbackRequest;
import ru.justai.vkechobot.enum_.EventType;

/**
 * Сервис для обработки входящих запросов от VK API.
 * Определяет тип события и направляет его на соответствующий процессор.
 */
@Service
@RequiredArgsConstructor
public class CallbackRequestProcessor {

    private static final String STATUS_OK = "ok";

    /**
     * Процессор для обработки событий подтверждения.
     */
    private final ConfirmationProcessor confirmationProcessor;

    /**
     * Процессор для обработки событий, не явялющихся событиями подтверждения.
     */
    private final EventProcessor eventProcessor;

    /**
     * Обрабатывает входящий запрос, определяет тип события и направляет его на соответствующий процессор.
     *
     * @param callbackRequest объект запроса с данными события
     * @return строка с результатом обработки события
     */
    public String process(CallbackRequest callbackRequest) {
        var event = callbackRequest.getObject();
        var eventType = callbackRequest.getType();
        return EventType.fromString(eventType)
                .map(type -> {
                    if (type.equals(EventType.CONFIRMATION)) {
                        return confirmationProcessor.process();
                    } else {
                        eventProcessor.process(event, eventType);
                        return STATUS_OK;
                    }
                })
                .orElse(STATUS_OK);
    }
}

