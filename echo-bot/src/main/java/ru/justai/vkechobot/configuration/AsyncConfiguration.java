package ru.justai.vkechobot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Конфигурация для включения асинхронной обработки в приложении.
 * Обеспечивает поддержку асинхронного выполнения методов через аннотацию {@link @Async}.
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {
}

