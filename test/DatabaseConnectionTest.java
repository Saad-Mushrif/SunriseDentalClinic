
import org.junit.Test;
import static org.junit.Assert.*;
import sunrisedentalclinic.database.DatabaseConnection;
import java.sql.Connection;

public class DatabaseConnectionTest {

    @Test
    public void testDatabaseConnectionIsNotNull() {

        Connection conn = DatabaseConnection.getInstance().getConnection();

        assertNotNull("The database connection should successfully connect to MySQL and not be null", conn);
    }
}
