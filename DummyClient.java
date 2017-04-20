import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DummyClient {

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Socket socket = new Socket("127.0.0.1", 8080);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		
		boolean running = true;
		
		Scanner innn = new Scanner(System.in);
		
		
		while(running){
			
		System.out.println("What would you like to do?");
		String command = innn.nextLine();
		
		switch(command){
		case "createAccount":
			System.out.println("Username of new account?");
			String u = innn.nextLine();
			System.out.println("Password of new account?");
			String p = innn.nextLine();
			
			out.writeObject(new Message("NEW"));
			out.flush();
			
			in.readObject();
			
			out.writeObject(new Account(u,p));
			out.flush();
			
			int res = ((Message)in.readObject()).getNumber();
			
			switch(res){
			case 2:
				System.out.println("Account created successfully");
				break;
				
			default:
				System.out.println("Account already exists");
				break;
			}
			break;
		
		case "exit":
			
			out.writeObject(new Message("EXIT"));
			out.flush();
			in.readObject();
			System.out.println("Exiting console");
			running = false;
		}
	}
		innn.close();
		
		
		
		
		
		
	}

}
