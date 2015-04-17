import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	private ArrayList<ClientThread> al;
	private int port;
	private boolean keepGoing;
	private static int uniqueId;
	public static DAO Database;
	public static void main(String[] args) {
		// start server on port 1500 unless a PortNumber is specified
                int portNumber = 1500;
                Database = new DAO();
		switch(args.length) {
                        case 1:
                                try {
                                        portNumber = Integer.parseInt(args[0]);
                                }
                                catch(Exception e) {
                                        System.out.println("Invalid port number.");
                                        System.out.println("Usage is: > java Server [portNumber]");
                                        return;
                                }
                        case 0:
                                break;
                        default:
                                System.out.println("Usage is: > java Server [portNumber]");
                                return;
                }
				Server server = new Server(portNumber);
                server.start();
	}
	
	public Server(int port)  {
                // the port
                this.port = port;
                // ArrayList for the Client list
                al = new ArrayList<ClientThread>();
			}
			
	public void start() {
		keepGoing = true;
		Scanner keyboard = new Scanner(System.in);
		// create socket server and wait for connection requests
		try {
			//the sockect used by the server
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server running on port " + port + "\n");
			//infinite loop to wait for connections
			while(keepGoing) {
				Socket socket = serverSocket.accept();
				ClientThread t = new ClientThread(socket);
				al.add(t);
				t.start();
			}
			try {
				serverSocket.close();
				for(int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
						tc.in.close();
						tc.out.close();
						tc.socket.close();
					}
					catch(IOException ioE) { }
				}
			}
			catch(Exception e) {
				System.out.println("Exception on closing the server and clients: "+ e);
			}
		}
		catch (IOException e) {
			System.out.println("Exception on new ServerSocket: " + e);
		}
	}
	
	synchronized void remove(int id)  {
		// scan the array list until we found the Id
		for(int i = 0; i < al.size(); ++i) {
				ClientThread ct = al.get(i);
				// found it
				if(ct.id == id) {
						al.remove(i);
						return;
				}
		}
	}
	
	class ClientThread extends Thread {
	// the socket where to listen/talk
	Socket socket;
	// my unique id (easier for deconnection)
	int id;
	// the Username of the Client
	String str;
	int index;
	PrintWriter out;
	BufferedReader in;

	FileInputStream fis = null;
	BufferedInputStream bis = null;

	// Constructor
	ClientThread(Socket socket) {
		// a unique id
		id = ++uniqueId;
		this.socket = socket;

		System.out.println("Client connected.");
		/* Creating both Data Stream */
		//System.out.println("Thread trying to create Object Input/Output Streams");
		try {
			// create output first
			//System.out.println("");
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch (IOException e) {
			System.out.println("Exception creating new Input/output Streams: " + e);
			return;
		}
	}
	public void run()  {
		// to loop until LOGOUT
		boolean keepGoing = true;
		str = "";
		boolean loggedIn = false;
		ArrayList<Route> routeList = new ArrayList<Route>();
		while(keepGoing) {
			// read a String (which is an object)
			try {
				str = in.readLine();
			}
			catch (IOException e) {
					System.out.println("Exception reading Streams: " + e);
					break;
			}
			System.out.println(str);
			if(str.equalsIgnoreCase("login")) {
				try {
					String username = in.readLine();
					String password = in.readLine();
					boolean result = Database.authenticate(username, password);
					if(result) {
						out.write("true\n");
						loggedIn = true;
					}
					else {
						out.write("false\n");
					}
					out.flush();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(str.equalsIgnoreCase("get routes")) {
				routeList = Database.getRoutes();
				int num = routeList.size();
				out.write(num + "\n");
				for(Route rt : routeList) {
					out.write(rt.getRname() + "\n");
				}
				out.flush();
			}
			if(str.equalsIgnoreCase("get shuttles")) {
				ArrayList<Shuttle> shuttles = Database.getShuttleList();
				int num = shuttles.size();
				out.write(num + "\n");
				for(Shuttle n : shuttles) {
					out.write(n.getId() + "\n");
					out.write(n.getCapacity() + "\n");
				}
				out.flush();
			}
			if(!loggedIn) {
				out.write("Not logged in. Closing connection");
				keepGoing = false;	
			}
			if(str.equalsIgnoreCase("route info")) {
				String route = "";
				try {
					 route = in.readLine();
				} catch(Exception e) {
					}
				System.out.println(route);
				for(Route rt : routeList) {
					if(route.equals(rt.getRname())) {
						out.write(rt.getLines() + "\n");
						out.write(rt.getCurves() + "\n");
						out.write(rt.getColor() + "\n");
					}
				}
				out.flush();
			}
			if(str.equalsIgnoreCase("capacity")) {
				String line = "";
				try {
					line = in.readLine();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
				System.out.println(line);
				int id = Integer.parseInt(line);
				String capLine = Database.getShuttleCapacity(id);
				out.write(capLine + "\n");
				out.flush();
				System.out.println(capLine);
			}
			if(str.equalsIgnoreCase("update")) {
				String line = "";
				try {
					line = in.readLine();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				int shuttle = Integer.parseInt(line);
				try {
					line = in.readLine();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				boolean Status = Boolean.parseBoolean(line);
				try {
					line = in.readLine();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				double lat = Double.parseDouble(line);
				try {
					line = in.readLine();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				double lon = Double.parseDouble(line);
				try {
					line = in.readLine();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				String routeName = line;
				try {
					line = in.readLine();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				int currentRiders = Integer.parseInt(line);
				try {
					line = in.readLine();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				int newRiders = Integer.parseInt(line);
				try {
					line = in.readLine();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				String username = line;
				String statusLine = "off-duty";
				if(Status) statusLine = "on-duty";
				Database.setShuttleInfo(shuttle, statusLine, lat, lon, currentRiders, newRiders, username);
			}
			if(str.equalsIgnoreCase("get interested")) {
				String route = "";
				try {
					route = in.readLine();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				ArrayList<Interest> interest = Database.getinterestedRiders(route);
				out.write(interest.size() + "\n");
				for(Interest i : interest) {
					out.write(i.getId() + "\n");
					out.write(i.getLatitude() + "\n");
					out.write(i.getLongitude() + "\n");
				}
				out.flush();
			}
			if(str.equalsIgnoreCase("remove interested")) {
				String line = "";
				try {
					line = in.readLine();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				int id = Integer.parseInt(line);
				Database.removeInterestedRider(id);
			}
			if(str.equalsIgnoreCase("close")) {
				keepGoing = false;
			}
		}
		remove(id);
		close();
	}
	private void close() {
		System.out.println("Client disconnected.");
		// try to close the connection
		try {
			if(out != null) out.close();
		}
		catch(Exception e) {}
		try {
			if(in != null) in.close();
		}
		catch(Exception e) {};
		try {
			if(socket != null) socket.close();
		}
		catch (Exception e) {}
	}
}
}
