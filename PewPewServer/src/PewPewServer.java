import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class PewPewServer {//central server
	
	//master list of all accounts on server, filled with record form accounts.txt
	private static ArrayList<Account> accountList = new ArrayList<Account>();
	//control for the main loop, is toggled to false in ServerConsole to stop the server
	private static boolean driverRunning = true;
	
	//this constructor is never actuallyused
	public PewPewServer(){
	
	}
	
		public static void main(String[] args) throws IOException {
			System.out.println("Server started");
			
			//loads accounts from accounts.txt into accountList
			BufferedReader br = new BufferedReader(new FileReader("accounts.txt"));
			String temp;
			String temp2;
			while ((temp = br.readLine()) != null) {
				temp2 = br.readLine();
				
				accountList.add(new Account(temp, temp2));
			}
			br.close();
			
			//displays number of accounts on the server on start
			if(accountList.size() == 1){
				System.out.println("Server contains 1 account");
			}else{
				System.out.println("Server contains "+ accountList.size() +" accounts");
			}
			
			//starts console
			new Thread(new ServerConsole()).start();
			
			
			//creates listener serverSocket and then constantly listens for connections and hands them off to handler threads
			//driverRunning is toggles to false in ServerConsole
			ServerSocket listener = new ServerSocket(8080);
			while(driverRunning){
				new Thread(new LoginHandler(listener.accept())).start();
			}
			
			
		}
		
		//if the account does not exist on the server, adds the account to the server
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
		
		//returns 0 if account matches username and password with one on the server, returns 1 if only username matches, returns 2 if username is not on the server
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
		
		//removes account from the server if the exact account exists on the server
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
		
		//returns the entire accountlist
		public static ArrayList<Account> getAccountList(){
			return accountList;
		}
		
		//sets driverRunning to false, used in ServerConsole
		public static void stopServer(){
			driverRunning = false;
		}
		
		
}
