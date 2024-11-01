import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.justai.vkechobot.processor.ConfirmationProcessor;
import ru.justai.vkechobot.service.ConfirmationService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConfirmationProcessorTest {

    private ConfirmationProcessor confirmationProcessor;

    private ConfirmationService confirmationService;

    @BeforeEach
    void setUp() {
        confirmationService = mock(ConfirmationService.class);
        confirmationProcessor = new ConfirmationProcessor(confirmationService);
    }

    @Test
    void process_returnsConfirmationString() {
        String expectedConfirmationString = "confirmation_string";
        when(confirmationService.handleConfirmation()).thenReturn(expectedConfirmationString);
        String result = confirmationProcessor.process();
        assertEquals(expectedConfirmationString, result);
        verify(confirmationService, times(1)).handleConfirmation();
    }
}
