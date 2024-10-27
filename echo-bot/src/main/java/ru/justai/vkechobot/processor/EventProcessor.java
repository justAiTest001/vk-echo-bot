package ru.justai.vkechobot.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.justai.vkechobot.enum_.CallbackType;
import ru.justai.vkechobot.dto.CallbackRequest;
import ru.justai.vkechobot.service.ConfirmationService;
import ru.justai.vkechobot.service.MessageService;

/**
 * Сервис для обработки различных типов событий, получаемых из вебхуков VK API.
 */
@Service
@RequiredArgsConstructor
public class EventProcessor {

    private static final String STATUS_OK = "ok";

    private final ConfirmationService confirmationService;

    private final MessageService messageService;

    /**
     * Обрабатывает входящее событие, определяя его тип и передавая на соответствующий сервис.
     *
     * @param callbackRequest запрос, содержащий данные о событии.
     * @return статус обработки: строка подтверждения для VK или "ok" при успешной обработке.
     */
    public String process(CallbackRequest callbackRequest) {
        var event = callbackRequest.getObject();
        return CallbackType.fromString(callbackRequest.getType())
                .map(callbackType ->
                        switch (callbackType) {
                            case CONFIRMATION -> confirmationService.handleConfirmation();
                            case MESSAGE_NEW -> {
                                messageService.handleNewMessage(event);
                                yield STATUS_OK;
                            }
                        })
                .orElse(STATUS_OK);
    }
}

