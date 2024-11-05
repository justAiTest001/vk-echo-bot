package ru.justai.vkechobot.event;

/**
 * Интерфейс для обработки событий.
 * Наследует {@link Event} и добавляет метод для обработки события.
 */
public interface EventHandler extends Event {

    /**
     * Обрабатывает переданное событие.
     *
     * @param event объект события для обработки
     */
    void handle(Object event);
}




