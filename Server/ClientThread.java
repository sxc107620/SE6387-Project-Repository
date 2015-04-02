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