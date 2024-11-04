package ru.justai.vkechobot.enum_;

/**
 * Перечисление типов событий, используемых для обработки событий VK API.
 * Определяет возможные типы событий и методы для работы с ними.
 */
public enum EventType {

    /**
     * Неизвестный тип события.
     */
    UNKNOWN("unknown"),

    /**
     * Событие подтверждения.
     */
    CONFIRMATION("confirmation"),

    /**
     * Событие нового сообщения.
     */
    MESSAGE_NEW("message_new");

    /**
     * Строковое представление типа события.
     */
    private final String type;

    /**
     * Конструктор перечисления с заданием строкового представления типа.
     *
     * @param type строковое представление типа события
     */
    EventType(String type) {
        this.type = type;
    }

    /**
     * Возвращает строковое значение типа события.
     *
     * @return строковое представление типа события
     */
    public String value() {
        return type;
    }

    /**
     * Возвращает объект {@link EventType} на основе переданной строки.
     * Сравнение производится без учета регистра.
     *
     * @param type строковое представление типа события
     * @return соответствующий объект {@link EventType} или {@code null}, если тип не найден
     */
    public static EventType fromString(String type) {
        for (EventType eventType : EventType.values()) {
            if (eventType.type.equalsIgnoreCase(type)) {
                return eventType;
            }
        }
        return null;
    }
}

