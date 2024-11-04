package ru.justai.vkechobot.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.justai.vkechobot.configuration.BotConfiguration.BotConfig;
import ru.justai.vkechobot.enum_.EventType;

/**
 * Обработчик события подтверждения.
 * Наследует {@link Event} и обрабатывает события типа CONFIRMATION.
 */
@Component
@RequiredArgsConstructor
public class EventConfirmator extends Event {

    /**
     * Конфигурация бота для получения строки подтверждения.
     */
    private final BotConfig botConfig;

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
        return botConfig.confirmationString();
    }
}


