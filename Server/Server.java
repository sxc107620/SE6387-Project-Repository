import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	private ArrayList<ClientThread> al;
	private int port;
	private boolean keepGoing;
	private static int uniqueId;

	public static void main(String[] args) {
		// start server on port 1500 unless a PortNumber is specified
                int portNumber = 1500;
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

		while(keepGoing) {
				// read a String (which is an object)
				try {

						str = in.readLine().toString();
				}
				catch (IOException e) {
						System.out.println("Exception reading Streams: " + e);
						break;
				}
				System.out.println(str);
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