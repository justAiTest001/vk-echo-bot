package ru.justai.vkechobot.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.justai.vkechobot.enum_.EventType;
import ru.justai.vkechobot.service.ConfirmationService;

/**
 * Обработчик события подтверждения.
 * Наследует {@link Event} и обрабатывает события типа CONFIRMATION.
 */
@Component
@RequiredArgsConstructor
public class EventConfirmator extends Event {

    /**
     * Сервис для обработки логики подтверждения.
     */
    private final ConfirmationService confirmationService;

    /**
     * Возвращает тип события CONFIRMATION.
     *
     * @return тип события {@link EventType#CONFIRMATION}
     */
    @Override
    public EventType type() {
        return EventType.CONFIRMATION;
    }

    /**
     * Возвращает строку подтверждения, определенную в конфигурации бота.
     *
     * @return строка подтверждения
     */
    public String confirmation() {
        return confirmationService.handleConfirmation();
    }
}


