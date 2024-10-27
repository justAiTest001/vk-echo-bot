package ru.justai.vkechobot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.justai.vkechobot.enum_.Method;
import ru.justai.vkechobot.util.Mapper;
import ru.justai.vkechobot.dto.Message;
import ru.justai.vkechobot.processor.VkApiClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Сервис для обработки новых сообщений от пользователей и отправки ответов через VK API.
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    private final VkApiClient vkApiClient;

    private final Mapper mapper;

    /**
     * Обрабатывает новое сообщение от пользователя, преобразуя его и отправляя в ответ.
     *
     * @param object объект, содержащий данные о новом сообщении.
     */
    public void handleNewMessage(Object object) {
        var message = mapper.mapNodeToObject(object, "message", Message.class);
        String userMessage = message.getText();
        sendMessage(message.getPeerId(), userMessage);
    }

    /**
     * Формирует и отправляет ответное сообщение пользователю через VK API.
     *
     * @param peerId идентификатор получателя сообщения.
     * @param text текст сообщения.
     */
    private void sendMessage(Long peerId, String text) {
        Map<String, String> params = new HashMap<>();
        params.put("peer_id", String.valueOf(peerId));
        params.put("message", "Вы сказали: " + text);
        params.put("random_id", String.valueOf(new Random().nextInt()));
        vkApiClient.sendRequest(Method.MESSAGES_SEND, params);
    }
}

