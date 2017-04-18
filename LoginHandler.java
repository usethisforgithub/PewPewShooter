import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginHandler implements Runnable{

	private Socket socket;
	
	public LoginHandler(Socket sock){
		socket = sock;
	}
	
	@Override
	public void run() {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(socket));
		
	}

}
