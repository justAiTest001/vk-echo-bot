import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.justai.vkechobot.dto.Message;
import ru.justai.vkechobot.exception.JsonMappingRuntimeException;
import ru.justai.vkechobot.util.Mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MapperTest {

    private Mapper mapper;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        mapper = new Mapper(objectMapper);
    }

    @Test
    public void testMapNodeToObjectSuccess() {
        String json = "{\"message\": {\"peer_id\": 123, \"text\": \"Hello\"}}";
        JsonNode rootNode = assertDoesNotThrow(() -> objectMapper.readTree(json));
        var message = mapper.mapNodeToObject(rootNode, "message", Message.class);
        assertNotNull(message);
        assertEquals(123L, message.getPeerId());
        assertEquals("Hello", message.getText());
    }

    @Test
    public void testMapNodeToObjectFailure() {
        String json = "{\"wrong_field\": {\"peer_id\": 123}}";
        JsonNode rootNode = assertDoesNotThrow(() -> objectMapper.readTree(json));
        assertThrows(JsonMappingRuntimeException.class, () ->
                mapper.mapNodeToObject(rootNode, "message", Message.class));
    }
}
