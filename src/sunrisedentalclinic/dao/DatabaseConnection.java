package sunrisedentalclinic.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection getConnection(){
        Connection con = null;
        try {
            Class.forName(
            "com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(
            "jdbc:mysql://localhost/sunrisedental_db",
            "root",
            "");
            System.out.println("Database Connected");
        }
        catch(Exception e){
            System.out.println(e);
        }
        return con;
    }
}
