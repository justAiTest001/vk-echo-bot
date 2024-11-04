package ru.justai.vkechobot.event;

import ru.justai.vkechobot.enum_.EventType;

/**
 * Абстрактный класс, представляющий событие.
 * Определяет тип события, который должен быть реализован в подклассах.
 */
public abstract class Event {

    /**
     * Возвращает тип события.
     *
     * @return тип события {@link EventType}
     */
    public abstract EventType type();
}

