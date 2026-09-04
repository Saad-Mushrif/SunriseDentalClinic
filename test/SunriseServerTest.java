
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;
import sunrisedentalclinic.server.SunriseServer;

public class SunriseServerTest {

    @Test
    public void testParseFormDataWithEmptyString() {

        String emptyPayload = "";

        Map<String, String> result = SunriseServer.parseFormData(emptyPayload);

        assertTrue("Parser should handle an empty string gracefully by returning an empty map", result.isEmpty());
    }
}
