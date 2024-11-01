import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.justai.vkechobot.dto.CallbackRequest;
import ru.justai.vkechobot.enum_.EventType;
import ru.justai.vkechobot.processor.CallbackRequestProcessor;
import ru.justai.vkechobot.processor.ConfirmationProcessor;
import ru.justai.vkechobot.processor.EventProcessor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CallbackRequestProcessorTest {

    private CallbackRequestProcessor callbackRequestProcessor;

    private ConfirmationProcessor confirmationProcessor;

    private EventProcessor eventProcessor;

    @BeforeEach
    void setUp() {
        confirmationProcessor = mock(ConfirmationProcessor.class);
        eventProcessor = mock(EventProcessor.class);
        callbackRequestProcessor = new CallbackRequestProcessor(confirmationProcessor, eventProcessor);
    }

    @Test
    void process_returnsConfirmation_whenEventTypeIsConfirmation() {
        CallbackRequest callbackRequest = new CallbackRequest();
        callbackRequest.setType(EventType.CONFIRMATION.value());
        when(confirmationProcessor.process())
                .thenReturn("confirmation_string");
        String result = callbackRequestProcessor.process(callbackRequest);
        assertEquals("confirmation_string", result);
        verify(confirmationProcessor, times(1)).process();
        verify(eventProcessor, never()).process(any(), any());
    }

    @Test
    void process_callsEventProcessor_whenEventTypeIsNotConfirmation() {
        CallbackRequest callbackRequest = new CallbackRequest();
        callbackRequest.setType(EventType.MESSAGE_NEW.value());
        Object event = new Object();
        callbackRequest.setObject(event);
        String result = callbackRequestProcessor.process(callbackRequest);
        assertEquals("ok", result);
        verify(eventProcessor, times(1)).process(event, EventType.MESSAGE_NEW.value());
        verify(confirmationProcessor, never()).process();
    }

    @Test
    void process_returnsOk_whenEventTypeIsUnknown() {
        CallbackRequest callbackRequest = new CallbackRequest();
        callbackRequest.setType("unknown_event_type");
        String result = callbackRequestProcessor.process(callbackRequest);
        assertEquals("ok", result);
        verify(confirmationProcessor, never()).process();
        verify(eventProcessor, never()).process(any(), any());
    }
}
