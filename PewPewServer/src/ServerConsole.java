import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerConsole implements Runnable{
	
	private boolean running;
	
	public ServerConsole(){
		running = true;
	}
	
	@Override
	public void run() {
		while(running){
		Scanner in = new Scanner(System.in);
		System.out.println("What would you like to do?");
		String command = in.nextLine();
		
		
		switch(command){
		case "showUsers":
			ArrayList<Account> accountList = PewPewServer.getAccountList();
			
			if(accountList.size() == 1){
				System.out.println("Server contains 1 account");
			}else{
				System.out.println("Server contains "+ accountList.size() +" accounts");
			}
			
			for(Account e: accountList){
				System.out.println(e.toString());
			}
			break;
			
		case "exit":
			System.out.println("Stopping server");
			PewPewServer.stopServer();
			running = false;
			
			//code to launch the last LoginHandler for current run
			
			Socket socket;
			try {
				socket = new Socket("127.0.0.1", 8080);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream innn = new ObjectInputStream(socket.getInputStream());
				
				out.writeObject(new Message("EXIT"));
				out.flush();
				

				
				
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			break;
			
			default:
				System.out.println("Comand does not exist for this system");
		}
	}
		
	}

}
