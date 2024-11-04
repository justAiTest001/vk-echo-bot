package ru.justai.vkechobot.event;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.justai.vkechobot.enum_.EventType;
import ru.justai.vkechobot.service.MessageService;

/**
 * Обработчик событий для новых сообщений.
 * Наследует {@link EventHandler} и обрабатывает события типа MESSAGE_NEW.
 */
@Component
@RequiredArgsConstructor
public class MessageEventHandler extends EventHandler {

    /**
     * Сервис для обработки сообщений.
     */
    private final MessageService messageService;

    /**
     * Возвращает тип события, обрабатываемого данным обработчиком — MESSAGE_NEW.
     *
     * @return тип события {@link EventType#MESSAGE_NEW}
     */
    @Override
    public EventType type() {
        return EventType.MESSAGE_NEW;
    }

    /**
     * Обрабатывает событие нового сообщения асинхронно.
     *
     * @param event объект события для обработки
     */
    @Override
    @Async
    public void handle(Object event) {
        messageService.handleNewMessage(event);
    }
}



