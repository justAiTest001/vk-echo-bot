package ru.justai.vkechobot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.justai.vkechobot.enum_.EventType;
import ru.justai.vkechobot.event.Event;
import ru.justai.vkechobot.event.EventHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Конфигурационный класс для настройки маппинга обработчиков событий.
 */
@Configuration
public class EventProcessorConfiguration {

    /**
     * Создает и настраивает мапу, сопоставляющую типы событий с их обработчиками.
     *
     * @param eventHandlers список всех доступных обработчиков событий
     * @return мапа, где ключом является тип события, а значением — соответствующий обработчик
     */
    @Bean
    public Map<EventType, Event> eventMap(List<Event> eventHandlers) {
        return eventHandlers.stream()
                .collect(Collectors.toMap(Event::type, Function.identity()));
    }
}


