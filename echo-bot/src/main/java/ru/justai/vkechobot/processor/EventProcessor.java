package ru.justai.vkechobot.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.justai.vkechobot.enum_.EventType;
import ru.justai.vkechobot.service.MessageService;


/**
 * Сервис для асинхронной обработки событий от VK API.
 * Направляет события на соответствующие сервисы для обработки.
 */
@Service
@RequiredArgsConstructor
public class EventProcessor {

    /**
     * Сервис для обработки новых сообщений.
     */
    private final MessageService messageService;

    /**
     * Асинхронно обрабатывает событие, определяет его тип и направляет на соответствующий метод обработки.
     *
     * @param event объект события
     * @param eventType строка, представляющая тип события
     */
    @Async
    public void process(Object event, String eventType) {
        EventType.fromString(eventType)
                .ifPresent(type -> {
                    switch (type) {
                        case MESSAGE_NEW -> messageService.handleNewMessage(event);
                    }
                });
    }
}


