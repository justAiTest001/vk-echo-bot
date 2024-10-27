package ru.justai.vkechobot.enum_;

import java.util.Optional;

/**
 * Перечисление типов событий обратного вызова (callback), отправляемых VK API.
 */
public enum CallbackType {

    CONFIRMATION("confirmation"),

    MESSAGE_NEW("message_new");

    private final String type;

    CallbackType(String type) {
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
     * Преобразует строку в соответствующий элемент перечисления CallbackType.
     *
     * @param type строка, представляющая тип события.
     * @return объект Optional с элементом CallbackType, если тип найден; иначе пустой Optional.
     */
    public static Optional<CallbackType> fromString(String type) {
        for (CallbackType callbackType : CallbackType.values()) {
            if (callbackType.type.equalsIgnoreCase(type)) {
                return Optional.of(callbackType);
            }
        }
        return Optional.empty();
    }
}
