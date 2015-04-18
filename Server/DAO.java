import java.sql.*;
import java.util.ArrayList;

public class DAO 
{
	public static void main(String[] args) {
		DAO d = new DAO();
		System.out.println(d.updatePassengerStatistics(101, 1));
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
	
	public ArrayList<Shuttle> getShuttleList()
    {
        Connection con = DAOConnection.getConnection();
        try
        {
        	ArrayList<Shuttle> routes = new ArrayList<Shuttle>();
        	
            String query = "SELECT number,type FROM shuttles;";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next())
            {
                int number = rs.getInt("number");
		String type = rs.getString("type");
		int cap = Character.getNumericValue(type.charAt(0));
                routes.add(new Shuttle(number, cap));
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
	
	public boolean setShuttleInfo(int shuttle_number, String status, double latitude, double longitude, int capacity, int newRiders, String user_name) {
		Connection con = DAOConnection.getConnection(); 
        try
        {
            String query = "UPDATE shuttles SET "
            		+ "status =? "
            		+ ",latitude =? "
            		+ ",longitude =? "
            		+ ",capacity =? "
			+ ",totalPassengers = totalPassengers+? "
            		+ "WHERE number =?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setDouble(2, latitude);
            preparedStatement.setDouble(3, longitude);
            preparedStatement.setInt(4, capacity);
	    preparedStatement.setInt(5, newRiders);
            preparedStatement.setInt(6, shuttle_number);
            preparedStatement.executeUpdate();
            closeConnection(con);
            
            return updatePassengerStatistics(shuttle_number, newRiders) && updateDriverStatistics(user_name, status);
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }    
        closeConnection(con);
        return false;
	}
	
	public String getShuttleCapacity(int shuttle_number) {
		Connection con = DAOConnection.getConnection();
        try
        {
            String query = "SELECT type FROM shuttles WHERE number =?;";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, shuttle_number);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next())
            {
                String type = rs.getString("type");
                closeConnection(con);
                return type;
            }
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
        closeConnection(con);
        return null;
	}
	
	public ArrayList<Interest> getinterestedRiders(String route_name) {
		Connection con = DAOConnection.getConnection();
        try
        {
        	ArrayList<Interest> interest = new ArrayList<Interest>();
        	
            String query = "SELECT id, interests.routeid, latitude, longitude FROM interests, routes WHERE interests.routeid = routes.routeid AND rname=?;";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, route_name);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next())
            {
                int id = rs.getInt("id");
                int rid = rs.getInt("routeid");
                double lat = rs.getDouble("latitude");
                double longit = rs.getDouble("longitude");
                interest.add(new Interest(id, rid, lat, longit));
            }
            closeConnection(con);
            return interest;
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
        closeConnection(con);
        return null;
	}
	
	public boolean removeInterestedRider(int interested_id) {
		Connection con = DAOConnection.getConnection(); 
        try
        {
            String query = "DELETE FROM interests WHERE id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, interested_id);
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
	
	public ArrayList<Route> getRoutes()
    {
        Connection con = DAOConnection.getConnection();
        try
        {
        	ArrayList<Route> routes = new ArrayList<Route>();
        	
            String query = "SELECT rname, color, savepoints, `lines`, curves FROM routes;";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next())
            {
                String name = rs.getString("rname");
                String color = rs.getString("color");
                String savepoints = rs.getString("savepoints");
                String lines = rs.getString("lines");
                String curves = rs.getString("curves");
                routes.add(new Route(name, color, savepoints, lines, curves));
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
	
	private boolean updatePassengerStatistics(int shuttle_number, int newRiders) {
    	if (newRiders > 0) {
			Connection con = DAOConnection.getConnection(); 
	        try
	        {
	            String query = "UPDATE statistics_passengers SET "
	            		+ "totalpassengers = totalpassengers + ? "
	            		+ "WHERE routeid = (SELECT routeid FROM shuttles WHERE shuttles.number =?) "
	            		+ "AND DATE_SUB(NOW(),INTERVAL 5 MINUTE) <= `timestamp`";
	            PreparedStatement preparedStatement = con.prepareStatement(query);
	            preparedStatement.setInt(1, newRiders);
	            preparedStatement.setInt(2, shuttle_number);
	            int num_updated = preparedStatement.executeUpdate();
	            
	            if (num_updated == 0) {
	            	query = "INSERT INTO statistics_passengers (routeid,`timestamp`,totalpassengers) VALUES ( "
	            			+ "(SELECT routeid FROM shuttles WHERE shuttles.number = ?),NOW(),?)";
	                preparedStatement = con.prepareStatement(query);
	                preparedStatement.setInt(1, shuttle_number);
	                preparedStatement.setInt(2, newRiders);
	                preparedStatement.executeUpdate();
	            }
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
    	return true;
	}

	private boolean updateDriverStatistics(String username, String status) {
		Connection con = DAOConnection.getConnection(); 
        try
        {
			if (status.equals("on-duty")) {
	            String query = "INSERT INTO statistics_driver_time (username, `date`,starttime) "
	            		+ "SELECT ?, CURRENT_DATE, CURRENT_TIME FROM statistics_driver_time "
	            		+ "WHERE NOT EXISTS (SELECT * FROM statistics_driver_time WHERE username = ? AND endtime IS NULL) "
	            		+ "LIMIT 1";
	            PreparedStatement preparedStatement = con.prepareStatement(query);
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, username);
	            preparedStatement.executeUpdate();
			} else {
	            String query = "UPDATE statistics_driver_time SET "
				+ "endtime = CURRENT_TIME, "
				+ "totaltime = TIME_TO_SEC(TIMEDIFF(CURRENT_TIME, (SELECT starttime))) * 1000 "
				+ "WHERE username = ? "
				+ "AND endtime IS NULL";
	            PreparedStatement preparedStatement = con.prepareStatement(query);
	            preparedStatement.setString(1, username);
	            preparedStatement.executeUpdate();
			}

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
