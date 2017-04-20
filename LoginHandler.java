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
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
			//System.out.println("got here server 0");
			while(running){
			String control = ((Message)in.readObject()).getText();
			Account temp = null;
			switch(control){
			case "LOGIN":
				
				
				
			break;
			
			case "NEW":
				
				out.writeObject(new Message("OKAY"));
				out.flush();
				
				temp = (Account)in.readObject();
				
				out.writeObject(new Message(ServerDriver.verifyAccount(temp)));
				out.flush();
				
				if(ServerDriver.verifyAccount(temp)==2){
					ServerDriver.createAccount(temp);
				}
				break;
				
			case "EXIT":
				running = false;
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
