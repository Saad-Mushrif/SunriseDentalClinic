
import org.junit.Test;
import static org.junit.Assert.*;
import sunrisedentalclinic.model.User;

public class UserTest {

    @Test
    public void testUserCreationAndInheritance() {

        User user = new User(1, "John Doe", "0771234567", "jdoe", "pass123", "Admin");

        assertEquals("Name should be inherited properly", "John Doe", user.getName());
        assertEquals("Role should be set correctly", "Admin", user.getRole());
    }
}
