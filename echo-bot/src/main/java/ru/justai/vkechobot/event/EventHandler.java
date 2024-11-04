package ru.justai.vkechobot.event;

/**
 * Абстрактный класс для обработки событий.
 * Наследует {@link Event} и добавляет метод для обработки события.
 */
public abstract class EventHandler extends Event {

    /**
     * Обрабатывает переданное событие.
     *
     * @param event объект события для обработки
     */
    public abstract void handle(Object event);
}




