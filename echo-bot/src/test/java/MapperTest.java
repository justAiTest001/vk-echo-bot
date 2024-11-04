import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.justai.vkechobot.dto.Message;
import ru.justai.vkechobot.dto.VkApiError;
import ru.justai.vkechobot.exception.JsonMappingRuntimeException;
import ru.justai.vkechobot.util.Mapper;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class MapperTest {

    private Mapper mapper;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        mapper = new Mapper(objectMapper);
    }

    @Test
    public void mapNodeToObject_success() {
        String json = "{\"message\": {\"peer_id\": 123, \"text\": \"Hello\"}}";
        JsonNode rootNode = assertDoesNotThrow(() -> objectMapper.readTree(json));
        var message = mapper.mapNodeToObject(rootNode, "message", Message.class);
        assertNotNull(message);
        assertEquals(123L, message.getPeerId());
        assertEquals("Hello", message.getText());
    }

    @Test
    public void mapNodeToObject_throwsJsonMappingRuntimeException_whenNodeNotFound() {
        String json = "{\"wrong_field\": {\"peer_id\": 123}}";
        JsonNode rootNode = assertDoesNotThrow(() -> objectMapper.readTree(json));
        assertThrows(JsonMappingRuntimeException.class, () ->
                mapper.mapNodeToObject(rootNode, "message", Message.class));
    }

    @Test
    public void extractError_returnsEmptyOptional_whenJsonIsValid() {
        String validJson = "{\"response\": {\"data\": \"Some valid response\"}}";
        ResponseEntity<String> response = ResponseEntity.ok(validJson);
        Optional<VkApiError> error = mapper.extractError(response);
        assertTrue(error.isEmpty(), "Expected an empty Optional when JSON is valid");
    }

    @Test
    public void extractError_returnsError_whenJsonContainsError() {
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
        ResponseEntity<String> response = ResponseEntity.ok(errorJson);
        Optional<VkApiError> error = mapper.extractError(response);
        assertTrue(error.isPresent(), "Expected an Optional with error when JSON contains an error");
        assertEquals(14, error.get().getCode());
        assertEquals("Captcha needed", error.get().getMessage());
    }
}
