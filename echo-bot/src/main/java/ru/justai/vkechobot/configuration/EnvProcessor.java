package ru.justai.vkechobot.configuration;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для обработки и добавления переменных среды из файла .env в среду выполнения Spring.
 * Реализует интерфейс {@link EnvironmentPostProcessor} для внедрения дополнительных источников свойств.
 */
public class EnvProcessor implements EnvironmentPostProcessor {

    /**
     * Загружает переменные из файла .env и добавляет их в среду выполнения приложения.
     *
     * @param environment объект {@link ConfigurableEnvironment}, в который добавляются переменные среды
     * @param application объект {@link SpringApplication}, связанный с контекстом приложения
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        Map<String, Object> dotenvProperties = new HashMap<>();
        dotenv.entries().forEach(entry -> dotenvProperties.put(entry.getKey(), entry.getValue()));
        environment.getPropertySources().addLast(new MapPropertySource("dotenvProperties", dotenvProperties));
    }
}



