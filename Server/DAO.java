import java.sql.*;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DAO 
{
	public void closeConnection(Connection c) {
    	try {
	    	if (c != null && !c.isClosed()) {
	    		c.close();
	    	}
    	} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			c = null;
		}
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
                    closeConnection(con);
                    return true;
                }
            }
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
        closeConnection(con);
        return false;
    }
}
