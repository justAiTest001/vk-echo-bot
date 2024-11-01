import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.justai.vkechobot.enum_.EventType;
import ru.justai.vkechobot.processor.EventProcessor;
import ru.justai.vkechobot.service.MessageService;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class EventProcessorTest {

    private EventProcessor eventProcessor;

    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageService = mock(MessageService.class);
        eventProcessor = new EventProcessor(messageService);
    }

    @Test
    void process_callsHandleNewMessage_whenEventTypeIsMessageNew() {
        Object event = new Object();
        String eventType = EventType.MESSAGE_NEW.value();
        eventProcessor.process(event, eventType);
        verify(messageService, times(1)).handleNewMessage(event);
    }

    @Test
    void process_doesNotCallHandleNewMessage_whenEventTypeIsUnknown() {
        Object event = new Object();
        String eventType = "unknown_event_type";
        eventProcessor.process(event, eventType);
        verify(messageService, never()).handleNewMessage(any());
    }
}
