import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.justai.vkechobot.configuration.BotConfiguration.BotConfig;
import ru.justai.vkechobot.service.ConfirmationService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConfirmationServiceTest {

    private ConfirmationService confirmationService;

    private BotConfig botConfig;

    @BeforeEach
    void setUp() {
        botConfig = mock(BotConfig.class);
        confirmationService = new ConfirmationService(botConfig);
    }

    @Test
    void testHandleConfirmationSuccess() {
        String expectedConfirmationString = "confirmation";
        when(botConfig.confirmationString())
                .thenReturn(expectedConfirmationString);
        String result = confirmationService.handleConfirmation();
        assertEquals(expectedConfirmationString, result);
        verify(botConfig, times(1)).confirmationString();
    }
}