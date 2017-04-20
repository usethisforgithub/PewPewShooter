import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginHandler implements Runnable{

	private Socket socket;
	private boolean running;
	
	public LoginHandler(Socket sock){
		socket = sock;
		running = true;
	}
	
	@Override
	
	public void run() {
		
		try {
			System.out.println("connected to server");
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
			//System.out.println("got here server 0");
			while(running){
			String control = ((Message)in.readObject()).getText();
			System.out.println("control: " + control);
			Account temp = null;
			switch(control){
			case "LOGIN":
				
				out.writeChars("OKAY");
				out.flush();
				
				
				temp = (Account)in.readObject();
				
				
				out.write(ServerDriver.verifyAccount(temp));
				
			break;
			
			case "NEW":
				System.out.println("got here server 1");
				out.writeObject(new Message("OKAY"));
				out.flush();
				System.out.println("got here server 2");
				temp = (Account)in.readObject();
				out.writeObject(new Message(ServerDriver.verifyAccount(temp)));
				out.flush();
				System.out.println("got here server 3");
				if(ServerDriver.verifyAccount(temp)==2){
					ServerDriver.createAccount(temp);
				}
				break;
				
			case "EXIT":
				running = false;
				System.out.println("Closing connection to client");
				break;
			}
		}
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
