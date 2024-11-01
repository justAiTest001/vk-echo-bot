package ru.justai.vkechobot.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.justai.vkechobot.configuration.BotConfiguration.BotConfig;
import ru.justai.vkechobot.dto.VkApiError;
import ru.justai.vkechobot.enum_.Method;
import ru.justai.vkechobot.exception.VkApiRequestRuntimeException;
import ru.justai.vkechobot.util.Mapper;
import ru.justai.vkechobot.util.UriUtil;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис-клиент для взаимодействия с VK API.
 * Выполняет отправку запросов и обработку ответов, обеспечивая валидацию и обработку ошибок.
 */
@Service
@RequiredArgsConstructor
public class VkApiClient {

    /**
     * Объект для выполнения HTTP-запросов.
     */
    private final RestTemplate restTemplate;

    /**
     * Утилита для создания URI.
     */
    private final UriUtil uriUtil;

    /**
     * Конфигурация бота, содержащая параметры API.
     */
    private final BotConfig botConfig;

    /**
     * Сервис для маппинга и извлечения ошибок из ответа.
     */
    private final Mapper mapper;

    /**
     * Отправляет запрос к VK API и возвращает ответ.
     *
     * @param method метод VK API, который нужно вызвать
     * @param params параметры запроса
     * @return ответ VK API в виде объекта {@link ResponseEntity}
     */
    public ResponseEntity<String> sendRequest(Method method, Map<String, String> params) {
        var uri = createUri(method);
        var payload = createPayload(params);
        HttpEntity<String> entity = createHttpEntity(payload);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        return getValidatedResponse(response);
    }

    /**
     * Создает URI для вызова метода VK API.
     *
     * @param method метод VK API
     * @return объект {@link URI}, представляющий URL запроса
     */
    private URI createUri(Method method) {
        String methodUrl = botConfig.apiUrl() + "/" + method.value();
        return uriUtil.createUri(methodUrl);
    }

    /**
     * Создает строку payload с параметрами запроса.
     *
     * @param params параметры запроса
     * @return строка с закодированными параметрами
     */
    private String createPayload(Map<String, String> params) {
        params.put("access_token", botConfig.accessToken());
        params.put("v", botConfig.apiVersion());
        return params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }

    /**
     * Создает HTTP-сущность для отправки запроса.
     *
     * @param payload тело запроса
     * @return объект {@link HttpEntity} с заголовками и телом запроса
     */
    private HttpEntity<String> createHttpEntity(String payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new HttpEntity<>(payload, headers);
    }

    /**
     * Проверяет ответ на наличие ошибок и возвращает его, если ошибок нет.
     * Если ошибка обнаружена, выбрасывает исключение {@link VkApiRequestRuntimeException}.
     *
     * @param response ответ VK API
     * @return валидированный ответ VK API
     */
    private ResponseEntity<String> getValidatedResponse(ResponseEntity<String> response) {
        var error = mapper.extractError(response);
        return Optional.of(response)
                .filter(res -> error.isEmpty())
                .orElseThrow(() -> new VkApiRequestRuntimeException(error.get()));
    }
}



//        if (attemptCounter.incrementAndGet() <= 4) {
//var vkError = new VkApiError();
//            vkError.setCode(503);
//            vkError.setMessage("kek");
//            throw new VkApiRequestRuntimeException(vkError);
//        }