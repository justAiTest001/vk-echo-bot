package ru.justai.vkechobot.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.justai.vkechobot.configuration.BotConfiguration.BotConfig;
import ru.justai.vkechobot.enum_.Method;
import ru.justai.vkechobot.exception.VkApiRequestRuntimeException;
import ru.justai.vkechobot.util.UriUtil;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Клиент для взаимодействия с VK API.
 * Выполняет отправку запросов и обработку ответов от API.
 */
@Service
@RequiredArgsConstructor
public class VkApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final UriUtil uriUtil;

    private final BotConfig botConfig;

    /**
     * Отправляет запрос к VK API с заданным методом и параметрами.
     *
     * @param method метод VK API для выполнения.
     * @param params параметры запроса.
     * @return ответ от VK API.
     */
    public ResponseEntity<String> sendRequest(Method method, Map<String, String> params) {
        var uri = createUri(method);
        var payload = createPayload(params);
        HttpEntity<String> entity = createHttpEntity(payload);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        return getValidatedResponse(response);
    }

    /**
     * Создаёт URI для указанного метода VK API.
     *
     * @param method метод VK API.
     * @return URI для выполнения запроса.
     */
    private URI createUri(Method method) {
        String methodUrl = botConfig.apiUrl() + "/" + method.value();
        return uriUtil.createUri(methodUrl);
    }

    /**
     * Формирует строку запроса из переданных параметров.
     *
     * @param params параметры запроса.
     * @return строка запроса в формате application/x-www-form-urlencoded.
     */
    private String createPayload(Map<String, String> params) {
        params.put("access_token", botConfig.accessToken());
        params.put("v", botConfig.apiVersion());
        return params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }

    /**
     * Создаёт HTTP-объект запроса с заголовками и телом.
     *
     * @param payload строка запроса.
     * @return объект HTTP-entity для выполнения запроса.
     */
    private HttpEntity<String> createHttpEntity(String payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new HttpEntity<>(payload, headers);
    }

    /**
     * Проверяет статус ответа и возвращает его, если успешный.
     *
     * @param response ответ от VK API.
     * @return валидный ответ от VK API.
     * @throws VkApiRequestRuntimeException если ответ содержит ошибку.
     */
    private ResponseEntity<String> getValidatedResponse(ResponseEntity<String> response) {
        return Optional.of(response)
                .filter(res -> HttpStatus.OK.equals(res.getStatusCode()))
                .orElseThrow(() ->
                        new VkApiRequestRuntimeException(HttpStatus.valueOf(response.getStatusCode().value())));
    }
}

