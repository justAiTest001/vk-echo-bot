package ru.justai.vkechobot.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import ru.justai.vkechobot.enum_.Method;
import ru.justai.vkechobot.util.IdGenerator;
import ru.justai.vkechobot.util.Mapper;
import ru.justai.vkechobot.dto.Message;
import ru.justai.vkechobot.client.VkApiClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Сервис для обработки новых сообщений от пользователей и отправки ответов через VK API.
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    /**
     * Клиент для взаимодействия с VK API.
     */
    private final VkApiClient vkApiClient;

    /**
     * Сервис для маппинга объектов.
     */
    private final Mapper mapper;

    /**
     * Генератор уникальных идентификаторов.
     */
    private final IdGenerator idGenerator;

    /**
     * Шаблон для выполнения повторных попыток отправки запроса.
     */
    private final RetryTemplate retryTemplate;

    /**
     * Обрабатывает новое сообщение, маппит объект и отправляет ответ пользователю.
     *
     * @param object объект, содержащий данные о событии сообщения
     */
    public void handleNewMessage(Object object) {
        logger.info("Начата обработка нового сообщения.");
        Message message = mapper.mapNodeToObject(object, "message", Message.class);
        String userMessage = message.getText();
        sendMessage(message.getPeerId(), userMessage);
    }

    /**
     * Отправляет сообщение пользователю через VK API с использованием повторных попыток.
     *
     * @param peerId идентификатор получателя сообщения
     * @param text   текст сообщения для отправки
     */
    private void sendMessage(Long peerId, String text) {
        Map<String, String> params = new HashMap<>();
        params.put("peer_id", String.valueOf(peerId));
        params.put("message", "Вы сказали: " + text);
        params.put("random_id", String.valueOf(idGenerator.generateRandomId(peerId)));
        retryTemplate.execute(context -> {
            logger.debug("Попытка отправки запроса через VK API.");
            vkApiClient.sendRequest(Method.MESSAGES_SEND, params);
            logger.info("Сообщение успешно отправлено пользователю с ID: {}", peerId);
            return null;
        });
    }
}


