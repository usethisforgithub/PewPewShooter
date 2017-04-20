
public class Message implements java.io.Serializable{
	private String text;
	private int number;
	
	public Message(String t){
		text = t;
	}
	
	public Message(int num){
		number = num;
	}
	
	public String getText(){
		return text;
	}
	
	public int getNumber(){
		return number;
	}
}
