import java.sql.*;

public class DAOConnection
{
    private static String url = "jdbc:mysql://localhost/cometride";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "cometdev";
    private static String password = "cometride";
    
    private static Connection con;    
    public static Connection getConnection() 
    {
        try 
        {
            Class.forName(driverName);
            try 
            {
                con = DriverManager.getConnection(url, username, password);
            }
            catch (SQLException ex) 
            {
                System.out.println("Unable to create db connection."); 
            }
        }
        catch (ClassNotFoundException ex) 
        {
            System.out.println("Driver not found."); 
        }
        return con;
    }
}

