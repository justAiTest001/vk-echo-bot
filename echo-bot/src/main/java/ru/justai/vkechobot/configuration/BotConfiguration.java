package ru.justai.vkechobot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для настроек бота VK.
 * Загружает параметры из application.properties и создает бин с конфигурацией бота.
 */
@Configuration
public class BotConfiguration {

    @Value("${vk.bot.access-token}")
    private String accessToken;

    @Value("${vk.api.url}")
    private String apiUrl;

    @Value("${vk.api.version}")
    private String apiVersion;

    @Value("${vk.confirmation.string}")
    private String confirmationString;

    /**
     * Создаёт бин с настройками бота VK.
     *
     * @return объект BotConfig с параметрами конфигурации бота.
     */
    @Bean
    public BotConfig botConfig() {
        return new BotConfig(accessToken, apiUrl, apiVersion, confirmationString);
    }

    /**
     * Записывает настройки бота VK, такие как токен доступа, URL API, версия API и строка подтверждения.
     *
     * @param accessToken токен доступа к боту VK.
     * @param apiUrl URL для обращения к VK API.
     * @param apiVersion версия VK API.
     * @param confirmationString строка подтверждения для настройки вебхука VK.
     */
    public record BotConfig(
            String accessToken,
            String apiUrl,
            String apiVersion,
            String confirmationString
    ) {
    }
}



