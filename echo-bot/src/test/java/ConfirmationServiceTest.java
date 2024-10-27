import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.justai.vkechobot.configuration.BotConfiguration.BotConfig;
import ru.justai.vkechobot.service.ConfirmationService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfirmationServiceTest {

    private ConfirmationService confirmationService;

    @BeforeEach
    public void setUp() {
        BotConfig botConfig = new BotConfig(
                "fake-token",
                "https://api.vk.com/method",
                "5.199",
                "confirmation_string"
        );
        confirmationService = new ConfirmationService(botConfig);
    }

    @Test
    public void testHandleConfirmation() {
        String result = confirmationService.handleConfirmation();
        assertEquals("confirmation_string", result);
    }
}
