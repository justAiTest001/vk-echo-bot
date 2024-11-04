import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.justai.vkechobot.dto.CallbackRequest;
import ru.justai.vkechobot.enum_.EventType;
import ru.justai.vkechobot.event.EventConfirmator;
import ru.justai.vkechobot.event.EventHandler;
import ru.justai.vkechobot.processor.EventProcessor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.justai.vkechobot.processor.EventRecognizer;

import java.util.Optional;

public class EventProcessorTest {

    private EventProcessor eventProcessor;

    @Mock
    private EventRecognizer eventRecognizer;

    @Mock
    private EventHandler eventHandler;

    @Mock
    private EventConfirmator eventConfirmator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventProcessor = new EventProcessor(eventRecognizer);
    }

    @Test
    void process_returnsConfirmation_whenEventTypeIsConfirmation() {
        CallbackRequest callbackRequest = new CallbackRequest();
        callbackRequest.setType(EventType.CONFIRMATION.value());
        callbackRequest.setObject(new Object());
        when(eventRecognizer.recognize(EventType.CONFIRMATION))
                .thenReturn(Optional.of(eventConfirmator));
        when(eventConfirmator.confirmation())
                .thenReturn("confirmation");
        when(eventConfirmator.type())
                .thenReturn(EventType.CONFIRMATION);
        String result = eventProcessor.process(callbackRequest);
        assertEquals("confirmation", result);
        verify(eventConfirmator, times(1)).confirmation();
    }

    @Test
    void process_callsEventHandler_whenEventTypeIsNotConfirmation() {
        CallbackRequest callbackRequest = new CallbackRequest();
        callbackRequest.setType(EventType.MESSAGE_NEW.value());
        callbackRequest.setObject(new Object());
        when(eventRecognizer.recognize(EventType.MESSAGE_NEW))
                .thenReturn(Optional.of(eventHandler));
        when(eventHandler.type())
                .thenReturn(EventType.MESSAGE_NEW);
        String result = eventProcessor.process(callbackRequest);
        assertEquals("ok", result);
        verify(eventHandler, times(1)).handle(any());
    }

    @Test
    void process_returnsOk_whenEventTypeIsUnknown() {
        CallbackRequest callbackRequest = new CallbackRequest();
        callbackRequest.setType("unknown_event_type");
        when(eventRecognizer.recognize(any())).thenReturn(Optional.empty());
        String result = eventProcessor.process(callbackRequest);
        assertEquals("ok", result);
        verify(eventHandler, never()).handle(any());
        verify(eventConfirmator, never()).confirmation();
    }
}

