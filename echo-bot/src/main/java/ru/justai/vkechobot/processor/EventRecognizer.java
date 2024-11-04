package ru.justai.vkechobot.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.justai.vkechobot.enum_.EventType;
import ru.justai.vkechobot.event.Event;

import java.util.Map;
import java.util.Optional;

/**
 * Сервис для распознавания и получения обработчика события по его типу.
 */
@Service
@RequiredArgsConstructor
public class EventRecognizer {

    /**
     * Мапа, содержащая обработчики событий, сопоставленные с их типами.
     */
    private final Map<EventType, Event> events;

    /**
     * Возвращает обработчик для указанного типа события, если он существует.
     *
     * @param eventType тип события для распознавания
     * @return {@link Optional} с обработчиком события, если он найден
     */
    public Optional<Event> recognize(EventType eventType) {
        return Optional.ofNullable(eventType)
                .flatMap(type -> Optional.ofNullable(events.get(type)));
    }
}


