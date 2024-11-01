import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.justai.vkechobot.client.VkApiClient;
import ru.justai.vkechobot.configuration.BotConfiguration;
import ru.justai.vkechobot.dto.VkApiError;
import ru.justai.vkechobot.enum_.Method;
import ru.justai.vkechobot.exception.VkApiRequestRuntimeException;
import ru.justai.vkechobot.util.Mapper;
import ru.justai.vkechobot.util.UriUtil;
import util.DtoUtil;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VkApiClientTest {

    private VkApiClient vkApiClient;

    private RestTemplate restTemplate;

    private Mapper mapper;

    private UriUtil uriUtil;

    private BotConfiguration.BotConfig botConfig;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        mapper = mock(Mapper.class);
        uriUtil = mock(UriUtil.class);
        botConfig = mock(BotConfiguration.BotConfig.class);
        vkApiClient = new VkApiClient(restTemplate, uriUtil, botConfig, mapper);
    }

    @Test
    void sendRequest_throwsVkApiRequestRuntimeException_whenResponseContainsJsonError() {
        String errorJson = """
                {
                  "error": {
                      "error_code":14,
                      "error_msg":"Captcha needed",
                      "request_params": [
                        {"key":"oauth", "value":"1"},
                        {"key":"method", "value":"captcha.force"},
                        {"key":"uids", "value":"66748"},
                        {"key":"access_token", "value":"example_token"}
                      ],
                      "captcha_sid":"239633676097",
                      "captcha_img":"http://api.vk.com/captcha.php?sid=239633676097&s=1"
                  }
                }
                """;
        ResponseEntity<String> errorResponse = ResponseEntity.ok(errorJson);
        when(botConfig.accessToken()).thenReturn("mockAccessToken");
        when(botConfig.apiVersion()).thenReturn("5.199");
        when(uriUtil.createUri(anyString())).thenReturn(URI.create("http://example.com"));
        VkApiError vkApiError = DtoUtil.createVkApiError();
        when(mapper.extractError(errorResponse)).thenReturn(Optional.of(vkApiError));
        when(restTemplate.exchange(any(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(errorResponse);
        Map<String, String> params = new HashMap<>();
        params.put("key", "value");
        VkApiRequestRuntimeException exception = assertThrows(VkApiRequestRuntimeException.class, () ->
                vkApiClient.sendRequest(Method.MESSAGES_SEND, params));
        assertEquals("Captcha needed", exception.getMessage());
        verify(restTemplate, times(1))
                .exchange(any(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
        verify(mapper, times(1)).extractError(errorResponse);
    }
}

