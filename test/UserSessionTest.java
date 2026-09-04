
import org.junit.Test;
import static org.junit.Assert.*;
import sunrisedentalclinic.model.User;
import sunrisedentalclinic.model.UserSession;

public class UserSessionTest {

    @Test
    public void testSingletonInstance() {

        UserSession instance1 = UserSession.getInstance();
        UserSession instance2 = UserSession.getInstance();

        assertNotNull("Singleton instance should not be null", instance1);
        assertSame("Both calls should return the exact same object in memory", instance1, instance2);
    }
}
