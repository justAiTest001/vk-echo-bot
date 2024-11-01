package ru.justai.vkechobot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.justai.vkechobot.dto.VkApiError;
import ru.justai.vkechobot.exception.JsonMappingRuntimeException;

import java.util.Optional;

/**
 * Компонент для маппинга JSON-узлов в объекты нужного типа.
 * Обрабатывает вложенные JSON-узлы и преобразует их в объекты Java.
 */
@Component
@RequiredArgsConstructor
public class Mapper {

    private static final String ERROR_PATH = "error";

    private final ObjectMapper objectMapper;

    /**
     * Преобразует указанный узел JSON в объект заданного типа.
     *
     * @param source     исходный объект, из которого будет извлечён JSON-узел.
     * @param nodeName   имя узла, который необходимо преобразовать.
     * @param objectType класс типа, в который нужно преобразовать узел.
     * @param <T>        тип целевого объекта.
     * @return объект типа T, полученный из JSON-узла.
     * @throws JsonMappingRuntimeException если возникла ошибка при маппинге.
     */
    public <T> T mapNodeToObject(Object source, String nodeName, Class<T> objectType) {
        try {
            var root = objectMapper.valueToTree(source);
            var node = root.path(nodeName);
            return objectMapper.treeToValue(node, objectType);
        } catch (JsonProcessingException e) {
            throw new JsonMappingRuntimeException(e);
        }
    }

    /**
     * Извлекает объект VkApiError из JSON-ответа.
     *
     * @param response тело ответа VK API в формате ResponseEntity<String>.
     * @return объект Optional<VkApiError>, если ошибка присутствует, иначе Optional.empty().
     */
    public Optional<VkApiError> extractError(ResponseEntity<String> response) {
        try {
            var root = objectMapper.readTree(response.getBody());
            return root.has(ERROR_PATH)
                    ? Optional.of(mapNodeToObject(root, ERROR_PATH, VkApiError.class))
                    : Optional.empty();
        } catch (JsonProcessingException e) {
            throw new JsonMappingRuntimeException(e);
        }
    }
}

