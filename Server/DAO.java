import java.sql.*;
import java.util.*;

public class DAO 
{
	
	public static void main(String [] args) {
		System.out.println(authenticate("admin", "admin"));
	}
	
	public boolean authenticate(String uname, String pwd)
    {
        Connection con = DAOConnection.getConnection();
        try
        {
            String query = "SELECT upass FROM users WHERE uname =?;";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, uname);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next())
            {
                String password = rs.getString("upass");
                if (password.equals(pwd)) {
                    c.close();
                    return true;
                }
            }
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
        c.close();
        return false;
    }
}