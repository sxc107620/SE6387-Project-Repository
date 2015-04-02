import java.sql.*;
import java.util.ArrayList;

public class DAO 
{
	public static void main(String[] args) {
		DAO d = new DAO();
		System.out.println(d.setShuttleInfo(401, "off-duty", 32.9861717224121, 96.7509689331055, 0));
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
	
	public boolean setShuttleRoute(int shuttle_number, String route_name) {
        Connection con = DAOConnection.getConnection(); 
        try
        {
            String query = "UPDATE shuttles "
            		+ "SET routeid=(SELECT routeid FROM routes WHERE rname=?) "
            		+ "WHERE number = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, route_name);
            preparedStatement.setInt(2, shuttle_number);
            preparedStatement.executeUpdate();
            closeConnection(con);
            return true;
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }    
        closeConnection(con);
        return false;
	}
	
	public ArrayList<Integer> getShuttleList()
    {
        Connection con = DAOConnection.getConnection();
        try
        {
        	ArrayList<Integer> routes = new ArrayList<Integer>();
        	
            String query = "SELECT number FROM shuttles;";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next())
            {
                int number = rs.getInt("number");
                routes.add(number);
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
	
	public boolean setShuttleInfo(int shuttle_number, String status, double latitude, double longitude, int capacity) {
		Connection con = DAOConnection.getConnection(); 
        try
        {
            String query = "UPDATE shuttles SET "
            		+ "status =? "
            		+ ",latitude =? "
            		+ ",longitude =? "
            		+ ",capacity =? "
            		+ "WHERE number =?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setDouble(2, latitude);
            preparedStatement.setDouble(3, longitude);
            preparedStatement.setInt(4, capacity);
            preparedStatement.setInt(5, shuttle_number);
            preparedStatement.executeUpdate();
            closeConnection(con);
            return true;
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }    
        closeConnection(con);
        return false;
	}
}
