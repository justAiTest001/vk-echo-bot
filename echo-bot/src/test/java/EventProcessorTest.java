import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.justai.vkechobot.dto.CallbackRequest;
import ru.justai.vkechobot.processor.EventProcessor;
import ru.justai.vkechobot.service.ConfirmationService;
import ru.justai.vkechobot.service.MessageService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class EventProcessorTest {

    private EventProcessor eventProcessor;

    @Mock
    private ConfirmationService confirmationService;

    @Mock
    private MessageService messageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        eventProcessor = new EventProcessor(confirmationService, messageService);
    }

    @Test
    public void testProcessConfirmationEvent() {
        CallbackRequest callbackRequest = new CallbackRequest();
        callbackRequest.setType("confirmation");
        when(confirmationService.handleConfirmation())
                .thenReturn("confirmation_string");
        String result = eventProcessor.process(callbackRequest);
        assertEquals("confirmation_string", result);
        verify(confirmationService, times(1))
                .handleConfirmation();
        verifyNoInteractions(messageService);
    }

    @Test
    public void testProcessMessageNewEvent() {
        CallbackRequest callbackRequest = new CallbackRequest();
        callbackRequest.setType("message_new");
        callbackRequest.setObject(new Object());
        String result = eventProcessor.process(callbackRequest);
        assertEquals("ok", result);
        verify(messageService, times(1))
                .handleNewMessage(any());
        verifyNoInteractions(confirmationService);
    }
}
