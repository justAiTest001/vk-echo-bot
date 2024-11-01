package ru.justai.vkechobot.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.justai.vkechobot.service.ConfirmationService;

/**
 * Сервис для обработки событий подтверждения от VK API.
 * Делегирует обработку подтверждений соответствующему сервису.
 */
@Service
@RequiredArgsConstructor
public class ConfirmationProcessor {

    /**
     * Сервис для обработки подтверждений.
     */
    private final ConfirmationService confirmationService;

    /**
     * Обрабатывает событие подтверждения, вызывая соответствующий метод сервиса.
     *
     * @return строка подтверждения, возвращаемая сервисом
     */
    public String process() {
        return confirmationService.handleConfirmation();
    }
}


