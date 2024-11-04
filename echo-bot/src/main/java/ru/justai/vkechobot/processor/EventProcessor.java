package ru.justai.vkechobot.processor;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.justai.vkechobot.dto.CallbackRequest;
import ru.justai.vkechobot.enum_.EventType;
import ru.justai.vkechobot.event.EventConfirmator;
import ru.justai.vkechobot.event.EventHandler;

/**
 * Сервис для распознавания и обработки событий, полученных от VK Callback API.
 */
@Service
@RequiredArgsConstructor
public class EventProcessor {

    private static final Logger logger = LoggerFactory.getLogger(EventProcessor.class);
    
    private static final String STATUS_OK = "ok";

    /**
     * Распознаватель событий.
     */
    private final EventRecognizer eventRecognizer;

    /**
     * Обрабатывает событие VK и возвращает статус обработки.
     * Выполняет асинхронную обработку события через соответствующий обработчик
     * и возвращает статус "ok" или строку подтверждения для событий типа CONFIRMATION.
     *
     * @param eventPrimal объект с данными события
     * @return строка статуса обработки события
     */
    public String process(CallbackRequest eventPrimal) {
        logger.info("Начата обработка события с типом: {}", eventPrimal.getType());
        EventType eventType = EventType.fromString(eventPrimal.getType());
        Object eventRaw = eventPrimal.getObject();
        return eventRecognizer.recognize(eventType)
                .map(event -> {
                    if (EventType.CONFIRMATION.equals(event.type())) {
                        logger.info("Обработка события подтверждения.");
                        return ((EventConfirmator) event).confirmation();
                    } else {
                        logger.info("Обработка события типа: {}", event.type());
                        ((EventHandler) event).handle(eventRaw); // выполняется асинхронно
                        return STATUS_OK;
                    }
                })
                .orElseGet(() -> {
                    logger.warn("Обработчик не найден для типа события: {}", eventPrimal.getType());
                    return STATUS_OK;
                });
    }
}

