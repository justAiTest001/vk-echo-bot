package ru.justai.vkechobot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.justai.vkechobot.configuration.BotConfiguration.BotConfig;

/**
 * Сервис для обработки события подтверждения от VK API.
 */
@Service
@RequiredArgsConstructor
public class ConfirmationService {

    private final BotConfig botConfig;

    /**
     * Обрабатывает запрос на подтверждение вебхука, возвращая строку подтверждения,
     * полученную из конфигурации.
     *
     * @return строка подтверждения для VK API.
     */
    public String handleConfirmation() {
        return botConfig.confirmationString();
    }
}

