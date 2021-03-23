import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class is responsible for creating and handling client threads.
 * It contains the client constructor and run method
 * 
 * @version 2-26-2021
 */
class ClientThread extends Thread {
	
	private Socket clientSocket;
	private BufferedReader in; 
	private PrintWriter out; 
	private String command;
	private long time; //reqTime
	
	
	// Thread Constructor //
	public ClientThread(String ip_address, int port, String command) throws IOException {
		
		clientSocket = new Socket(ip_address, port);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		this.command = command;
	}
	
	// run method -- entry point to thread //
	@Override
	public void run() {
		try {
			
			String response = "";
			System.out.println("-------------------");
			
			long startTime = System.currentTimeMillis();

			out.println(command);
			
			while ((response = in.readLine()).compareTo("[COMPLETED]") != 0)  {
			    System.out.println(response);
			}
			
			this.time = (System.currentTimeMillis()) - startTime;
			System.out.println("Turn Around time: " + time + "ms");
			
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public long getTurnAround() {
		return time;
	}
	
} 