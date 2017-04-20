import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerDriver {
	
	private static ArrayList<Account> accountList = new ArrayList<Account>();
	private static boolean driverRunning = true;
	
	public ServerDriver(){
		
		//accountList.add(new Account());
	}
	
		public static void main(String[] args) throws IOException {
			
			
			//load existing accounts from file
			
			
			BufferedReader br = new BufferedReader(new FileReader("accounts.txt"));
			
			String temp;
			String temp2;
			while ((temp = br.readLine()) != null) {
				temp2 = br.readLine();
				
				accountList.add(new Account(temp, temp2));
			}
			br.close();
			
			if(accountList.size() == 1){
				System.out.println("Server contains 1 account");
			}else{
				System.out.println("Server contains "+ accountList.size() +" accounts");
			}
			
			for(Account e: accountList){
				System.out.println(e.toString());
			}
			
			
			
			
			ServerSocket listener = new ServerSocket(8080);
			
			System.out.println("Server started");

			new Thread(new ServerConsole()).start();
			
			
			while(driverRunning){
				new Thread(new LoginHandler(listener.accept())).start();
				System.out.println("Tried to spawn handler");
			}
			
			
		}
		
		public static void createAccount(Account account){
			if(verifyAccount(account) == 2){
				accountList.add(account);
				
				try
				{
				    String filename= "accounts.txt";
				    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
				    BufferedWriter bw = new BufferedWriter(fw);
				    
				    bw.write(account.getUserName());
				    bw.newLine();
				    bw.write(account.getPassWord());
				    bw.newLine();
				    bw.close();
				}
				catch(IOException ioe)
				{
				    System.err.println("IOException: " + ioe.getMessage());
				}
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
			
			
			
			//if it reaches this return statement, accountList is empty
			return 2;
		}
		
		public void deleteAccount(Account account){
			for(Account e: accountList){
				if(e.equals(account)){
					accountList.remove(e);
					
					try
					{
					    String filename= "accounts.txt";
					    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
					    BufferedWriter bw = new BufferedWriter(fw);
					    
					    for(Account acc : accountList){
					    	bw.write(acc.getUserName());
					    	bw.newLine();
					    	bw.write(acc.getPassWord());
					    	bw.newLine();
					    }
					    
					    bw.close();
					}
					catch(IOException ioe)
					{
					    System.err.println("IOException: " + ioe.getMessage());
					}
				}
			}
		}
		
		public static ArrayList<Account> getAccountList(){
			return accountList;
		}
		
		public static void stopServer(){
			driverRunning = false;
		}
		
		
}
