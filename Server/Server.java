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
}