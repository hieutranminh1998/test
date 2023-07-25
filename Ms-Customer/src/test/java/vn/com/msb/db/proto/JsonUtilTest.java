package vn.com.msb.db.proto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class JsonUtilTest {

    @Mock
    ObjectMapper mapper;
    @Test
    void testToString() throws JsonProcessingException {
        assertEquals(JsonUtil.toString(null), "");
        assertEquals(JsonUtil.toString("abc"), "abc");
        assertEquals(JsonUtil.toString(new GRPCRequest().customerId), "");
    }

    @Test
    void toObject() {
        assertNull(JsonUtil.toObject(null, GRPCRequest.class));
        assertNotEquals(JsonUtil.toObject(JsonUtil.toString(new GRPCRequest()), GRPCRequest.class), null);
    }
}