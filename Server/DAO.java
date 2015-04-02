import java.sql.*;
import java.util.ArrayList;

public class DAO 
{
	public static void main(String[] args) {
		DAO d = new DAO();
		System.out.println(d.getRouteNameList());
	}
	
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
	
	public ArrayList<String> getRouteNameList()
    {
        Connection con = DAOConnection.getConnection();
        try
        {
        	ArrayList<String> routes = new ArrayList<String>();
        	
            String query = "SELECT rname FROM routes;";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next())
            {
                String name = rs.getString("rname");
                routes.add(name);
            }
            closeConnection(con);
            return routes;
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
        closeConnection(con);
        return null;
    }
	
	public Integer getRouteIdFromName(String name) {
		Connection con = DAOConnection.getConnection();
        try
        {
            String query = "SELECT routeid FROM routes WHERE rname =?;";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next())
            {
                int id = rs.getInt("rname");
                closeConnection(con);
                return id;
            }
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
        closeConnection(con);
        return null;
	}
}
