package ru.justai.vkechobot.enum_;

import java.util.Optional;

/**
 * Перечисление типов событий обратного вызова (callback), отправляемых VK API.
 */
public enum EventType {

    CONFIRMATION("confirmation"),

    MESSAGE_NEW("message_new");

    private final String type;

    EventType(String type) {
        this.type = type;
    }

    /**
     * Возвращает строковое представление типа события.
     *
     * @return строка, представляющая тип события.
     */
    public String value() {
        return type;
    }

    /**
     * Преобразует строку в соответствующий элемент перечисления EventType.
     *
     * @param type строка, представляющая тип события.
     * @return объект Optional с элементом EventType, если тип найден; иначе пустой Optional.
     */
    public static Optional<EventType> fromString(String type) {
        for (EventType eventType : EventType.values()) {
            if (eventType.type.equalsIgnoreCase(type)) {
                return Optional.of(eventType);
            }
        }
        return Optional.empty();
    }
}
