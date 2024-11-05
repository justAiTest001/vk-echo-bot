package ru.justai.vkechobot.event;

import ru.justai.vkechobot.enum_.EventType;

/**
 * Интерфейс, представляющий событие.
 * Определяет тип события, который должен быть реализован в классах, реализующих интерфейс.
 */
public interface Event {

    /**
     * Возвращает тип события.
     *
     * @return тип события {@link EventType}
     */
   EventType type();
}

