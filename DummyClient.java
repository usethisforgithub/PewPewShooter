import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class DummyClient {

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Socket socket = new Socket("192.168.1.115", 8080);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		
		
		out.writeObject(new Message("NEW"));
		out.flush();
		System.out.println("got here client 1");
		in.readObject();
		System.out.println("got here client 2");
		out.writeObject(new Account("TallaWalla","hi"));
		out.flush();
		System.out.println("got here client 3");
		int res = ((Message)in.readObject()).getNumber();
		
		System.out.println("result: " + res);
		
		out.writeObject(new Message("Exit"));
		out.flush();
		
		in.readObject();
	}

}
