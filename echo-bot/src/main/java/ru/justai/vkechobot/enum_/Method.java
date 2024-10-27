package ru.justai.vkechobot.enum_;

/**
 * Перечисление методов VK API для взаимодействия с различными функциями платформы.
 */
public enum Method {

    MESSAGES_SEND("messages.send"),

    GROUPS_GET_BY_ID("groups.getById"),

    PHOTOS_GET("photos.get");

    private final String value;

    Method(String value) {
        this.value = value;
    }

    /**
     * Возвращает строковое представление метода VK API.
     *
     * @return строка, представляющая метод VK API.
     */
    public String value() {
        return value;
    }
}

