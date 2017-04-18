import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class PewPewCentralServer implements Runnable{

	private static ArrayList<Account> accountList = new ArrayList<Account>();
	private ServerSocket listener;
	
	public PewPewCentralServer() throws IOException{
	//load existing accounts from file
		
		listener = new ServerSocket(8080);
	}
	
	
	
	@Override
	public void run() {
		try {
			new Thread(new LoginHandler(listener.accept())).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
	}

	public static void createAccount(Account account){
		if(verifyAccount(account) == 2){
			accountList.add(account);
		}
	}
	
	
	public static int verifyAccount(Account account){
		//if username and password match return 0
		for(Account e:accountList){
			if(e.getUserName().equals(account.getUserName())){
				if(e.getPassWord().equals(account.getPassWord())){
					return 0;
				}else{
					return 1;
				}
			}else{
				return 2;
			}
		}
		
		//if username exists but password doesn't match return 1
		//if username doesn't exist return 2
		//if it returns -100, something probably went terribly wrong
		return -100;
	}

}
