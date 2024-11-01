import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import ru.justai.vkechobot.client.VkApiClient;
import ru.justai.vkechobot.configuration.RetryConfiguration;
import ru.justai.vkechobot.dto.Message;
import ru.justai.vkechobot.dto.VkApiError;
import ru.justai.vkechobot.exception.VkApiRequestRuntimeException;
import ru.justai.vkechobot.service.MessageService;
import ru.justai.vkechobot.util.IdGenerator;
import ru.justai.vkechobot.util.Mapper;
import util.DtoUtil;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {RetryConfiguration.class})
class MessageServiceTest {

    private MessageService messageService;

    private VkApiClient vkApiClient;

    private Mapper mapper;

    private IdGenerator idGenerator;

    @Autowired
    private RetryTemplate retryTemplate;

    @BeforeEach
    void setUp() {
        vkApiClient = mock(VkApiClient.class);
        mapper = mock(Mapper.class);
        idGenerator = mock(IdGenerator.class);
        messageService = new MessageService(vkApiClient, mapper, idGenerator, retryTemplate);
    }

    @Test
    void handleNewMessage_success() {
        prepareMockMessage();
        when(vkApiClient.sendRequest(any(), any()))
                .thenReturn(ResponseEntity.ok("Success"));
        assertDoesNotThrow(() -> messageService.handleNewMessage(new Object()));
        verify(vkApiClient, times(1)).sendRequest(any(), any());
    }

    @Test
    void handleNewMessage_retriesTwiceBeforeSuccess() {
        prepareMockMessage();
        VkApiError vkApiError = prepareVkApiError();
        doThrow(new VkApiRequestRuntimeException(vkApiError))
                .doThrow(new VkApiRequestRuntimeException(vkApiError))
                .doReturn(ResponseEntity.ok("Success"))
                .when(vkApiClient).sendRequest(any(), any());
        assertDoesNotThrow(() -> messageService.handleNewMessage(new Object()));
        verify(vkApiClient, times(3)).sendRequest(any(), any());
    }

    @Test
    void handleNewMessage_retriesFiveTimesAndFails() {
        prepareMockMessage();
        VkApiError vkApiError = prepareVkApiError();
        doThrow(new VkApiRequestRuntimeException(vkApiError))
                .when(vkApiClient).sendRequest(any(), any());
        assertThrows(VkApiRequestRuntimeException.class, () -> messageService.handleNewMessage(new Object()));
        verify(vkApiClient, times(5)).sendRequest(any(), any());
    }

    private Message prepareMockMessage() {
        Message mockMessage = new Message();
        mockMessage.setPeerId(12345L);
        mockMessage.setText("Hello");
        when(mapper.mapNodeToObject(any(), eq("message"), eq(Message.class)))
                .thenReturn(mockMessage);
        when(idGenerator.generateRandomId(12345L))
                .thenReturn(100);
        return mockMessage;
    }

    private VkApiError prepareVkApiError() {
        return DtoUtil.createVkApiError();
    }
}
