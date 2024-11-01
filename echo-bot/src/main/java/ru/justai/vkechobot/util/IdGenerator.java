package ru.justai.vkechobot.util;

import org.springframework.stereotype.Component;
import ru.justai.vkechobot.exception.IdGenerationRuntimeException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Компонент для генерации уникальных идентификаторов (random_id) для отправки сообщений через VK API.
 * Обеспечивает уникальность идентификаторов для конкретного peerId в пределах последнего часа,
 * предотвращая дублирование сообщений.
 */
@Component
public class IdGenerator {

    /**
     * Счетчик для отслеживания текущего количества генераций.
     * Перезапускается при достижении максимального значения {@link Integer#MAX_VALUE}.
     */
    private final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Генерирует уникальный random_id для отправки сообщений VK API.
     * random_id гарантирует уникальность для конкретного peerId в пределах последнего часа.
     *
     * @param peerId идентификатор получателя сообщения
     * @return уникальный int random_id
     */
    public int generateRandomId(Long peerId) {
        long currentTimeMillis = System.currentTimeMillis();
        int currentCount = counter.getAndUpdate(count ->
                (count == Integer.MAX_VALUE) ? 0 : count + 1);
        return generateHashBasedId(currentTimeMillis, peerId, currentCount);
    }

    /**
     * Генерирует hash-based random_id на основе текущего времени, идентификатора получателя и счетчика.
     *
     * @param currentTimeMillis текущее время в миллисекундах
     * @param peerId идентификатор получателя сообщения
     * @param currentCount текущее значение счетчика
     * @return уникальный int random_id, основанный на хэшировании данных
     * @throws IdGenerationRuntimeException выбрасывается при ошибке генерации хэша
     */
    private int generateHashBasedId(long currentTimeMillis, long peerId, long currentCount) {
        try {
            String input = currentTimeMillis + ":" + peerId + ":" + currentCount;
            var md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, hash);
            return number.intValue();
        } catch (NoSuchAlgorithmException e) {
            throw new IdGenerationRuntimeException(e);
        }
    }
}


