import java.io.*;
import java.util.*;

/**
 * This class is responsible for creating a client connection
 * 
 * @version 2-26-2021
 */
public class Client {
	
	static ArrayList<Long> totalTimes = new ArrayList<Long>();
		
	/**
	 * This method is responsible for setting up the client connection and getting the desired command/amount of clients
	 */
	public static void main(String[] args) throws IOException {
	
		String ip = getClientIP();
		int port = getClientPort();
	
		Scanner in = new Scanner(System.in);
		System.out.println("Client(s) connection to the server will be established once a valid command is entered.\n");
		
		String command;
		while(true){
					
			displayMenu();
			command = in.nextLine();
			
			if(command.compareTo("7") == 0){

				ClientThread terminate = new ClientThread(ip, port, command);
				terminate.start(); //this would allow the 7 to be passed to the server, letting it know it can terminate both sockets. 
				
				try {
					terminate.join();
					in.close();
					System.exit(0);
					
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}				
				
		
			} else if(command.compareTo("1") == 0 || command.compareTo("2") == 0 || command.compareTo("3") == 0 || command.compareTo("4") == 0 || command.compareTo("5") == 0 || command.compareTo("6") == 0) {
				
				totalTimes.clear();
				int clientCount = getNumberOfClients();
				
				ClientThread[] clientPool = new ClientThread[clientCount];
				
				for(int i = 0; i < clientCount; i++) {
					
					clientPool[i] = new ClientThread(ip, port, command);
					System.out.println("Client number: " + (i + 1));
					clientPool[i].start(); 
				
					try {
						
						clientPool[i].join();
						
					} catch (InterruptedException e) {
					
						e.printStackTrace();
					}
					
					System.out.println();
					totalTimes.add(clientPool[i].getTurnAround());
				}
				
				int totalTime = 0;
				
				for(int i = 0; i < totalTimes.size(); i++) {
					totalTime += totalTimes.get(i);
				}
				
				int meanTotalTime = totalTime / totalTimes.size();
				
				System.out.println("-------------------------------");
				System.out.println("Average turn around time: " + meanTotalTime + "ms");
				System.out.println("Total turn around time: " + totalTime + "ms");
				System.out.println("-------------------------------");
				System.out.println();
				

				
			}else {
				System.out.println("Invalid Entry... please enter a command between 1 and 7.");
				
			} 

		} 
		
	}
	
	/**
	 * This method is responsible for getting the server's IP address from the user
	 * It runs when any IP is entered
	 * @return clientCount
	 */
	private static int getNumberOfClients() {
		
		int clientCount;	
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the number of clients you would like to test...");
		System.out.println("Valid between the range 1 - 100");
		
		do {
			
			System.out.println("Please enter a number within the valid range...");
			clientCount = in.nextInt();
				
		} while(clientCount <= 0 || clientCount > 100);
		
		return clientCount;
	}


	/**
	 * This method is responsible for getting the server's IP address from the user
	 * It runs when any IP is entered
	 * @return ip
	 */
	private static String getClientIP() {
		
		String ip = null;
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the server's IP address...");
		
		do {
			
			ip = in.nextLine();
			
		}while(ip == null);
		
		System.out.println("IP Address Entered: " + ip);
		return ip;
	}


	/**
	 * This method is responsible for getting the port number from the user
	 * It runs until a valid port is given
	 * @return port
	 */
	private static int getClientPort() {
		
		int port;	
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter a valid port number to begin establishing the client...");
		System.out.println("Valid entry between 1025 - 4998");
		
		do {
				
			System.out.println("Waiting for valid port number...");
			port = in.nextInt();
				
		} while(port < 1025 || port > 4998);
		
		System.out.println("Valid port number accepted...");
		return port;
	}

	/**
	 * This method is responsible for displaying the menu to the user
	 * @return none
	 */
	public static void displayMenu() {
		System.out.println("Review and select from list of commands using the associated number:");
		System.out.println("1. Date and Time - the date and time on the server ");
		System.out.println("2. Uptime - how long the server has been running since last boot-up");
		System.out.println("3. Memory Use - the current memory usage on the server");
		System.out.println("4. Netstat - lists network connections on the server");
		System.out.println("5. Current Users - list of users currently connected to the server");
		System.out.println("6. Running Processes - list of programs currently running on the server");
		System.out.print("7. To Terminate Server\n > ");
	}
}
