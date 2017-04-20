
public class Account implements java.io.Serializable{

	private String userName;
	private String passWord;
	
	public Account(String us, String pa){
		userName = us;
		passWord = pa;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public String getPassWord(){
		return passWord;
	}
	
	
	public String toString(){
		return "Username: " + userName + " Password: " + passWord;
	}
	
	
}
