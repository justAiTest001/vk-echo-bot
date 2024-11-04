import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.justai.vkechobot.enum_.EventType;
import ru.justai.vkechobot.event.Event;
import ru.justai.vkechobot.processor.EventRecognizer;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EventRecognizerTest {

    private EventRecognizer eventRecognizer;

    @Mock
    private Event messageEventHandler;

    @Mock
    private Event confirmationEventHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Map<EventType, Event> events = Map.of(
                EventType.MESSAGE_NEW, messageEventHandler,
                EventType.CONFIRMATION, confirmationEventHandler
        );
        eventRecognizer = new EventRecognizer(events);
    }

    @Test
    void recognize_returnsHandler_whenEventTypeExists() {
        Optional<Event> result = eventRecognizer.recognize(EventType.MESSAGE_NEW);
        assertTrue(result.isPresent());
        assertEquals(messageEventHandler, result.get());
    }

    @Test
    void recognize_returnsConfirmationHandler_whenEventTypeIsConfirmation() {
        Optional<Event> result = eventRecognizer.recognize(EventType.CONFIRMATION);
        assertTrue(result.isPresent());
        assertEquals(confirmationEventHandler, result.get());
    }

    @Test
    void recognize_returnsEmpty_whenEventTypeDoesNotExist() {
        Optional<Event> result = eventRecognizer.recognize(EventType.UNKNOWN);
        assertTrue(result.isEmpty());
    }

    @Test
    void recognize_returnsEmpty_whenEventTypeIsNull() {
        Optional<Event> result = eventRecognizer.recognize(null);
        assertTrue(result.isEmpty());
    }
}
