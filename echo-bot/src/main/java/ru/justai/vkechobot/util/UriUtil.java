package ru.justai.vkechobot.util;

import org.springframework.stereotype.Component;
import ru.justai.vkechobot.exception.InvalidUriRuntimeException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Утилитный компонент для создания URI объектов из строковых представлений.
 */
@Component
public class UriUtil {

    /**
     * Создаёт URI из переданной строки.
     *
     * @param uri строка, представляющая URI.
     * @return объект URI.
     * @throws InvalidUriRuntimeException если строка не является допустимым URI.
     */
    public URI createUri(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new InvalidUriRuntimeException(uri, e);
        }
    }
}

