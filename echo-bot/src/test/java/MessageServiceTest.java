import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.justai.vkechobot.dto.Message;
import ru.justai.vkechobot.enum_.Method;
import ru.justai.vkechobot.processor.VkApiClient;
import ru.justai.vkechobot.service.MessageService;
import ru.justai.vkechobot.util.Mapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MessageServiceTest {

    private MessageService messageService;

    @Mock
    private VkApiClient vkApiClient;

    @Mock
    private Mapper mapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        messageService = new MessageService(vkApiClient, mapper);
    }

    @Test
    public void testHandleNewMessage() {
        var message = new Message();
        message.setPeerId(123L);
        message.setText("Hello");
        when(mapper.mapNodeToObject(any(), eq("message"), eq(Message.class)))
                .thenReturn(message);
        messageService.handleNewMessage(new Object());
        verify(vkApiClient, times(1))
                .sendRequest(eq(Method.MESSAGES_SEND), anyMap());
    }
}

