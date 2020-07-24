import helpers.DataFormParser;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataFormParserTest {

    @Test
    public void parseFormData() throws UnsupportedEncodingException {
        Map<String, String> expected = new HashMap<>();
        expected.put("login", "test");
        expected.put("password", "123123123");
        String path = "login=test&password=123123123";
        Map<String, String> result = DataFormParser.parseFormData(path);

        assertEquals(expected, result);
    }

    @Test
    public void parseFormDataNotEquals() throws UnsupportedEncodingException {
        Map<String, String> expected = new HashMap<>();
        expected.put("login", "test");
        String path = "login=test&password=123123123";
        Map<String, String> result = DataFormParser.parseFormData(path);
        assertNotEquals(expected, result);
    }
}