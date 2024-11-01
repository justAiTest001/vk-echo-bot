package ru.justai.vkechobot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.justai.vkechobot.configuration.BotConfiguration.BotConfig;

/**
 * Сервис для обработки подтверждающих строк, необходимых для интеграции с VK API.
 * Предоставляет строку подтверждения для верификации.
 */
@Service
@RequiredArgsConstructor
public class ConfirmationService {

    /**
     * Конфигурация бота, содержащая строку подтверждения.
     */
    private final BotConfig botConfig;

    /**
     * Возвращает строку подтверждения, необходимую для подтверждения запроса VK API.
     *
     * @return строка подтверждения из конфигурации бота
     */
    public String handleConfirmation() {
        return botConfig.confirmationString();
    }
}

