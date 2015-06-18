import java.io.Serializable;


public class LoginData implements Serializable{

	protected String localIP;
	protected char[] password;
	
	void setLocalIP(String temp){
		this.localIP = temp;
	}
	
	void setPassword(char[] temp){
		this.password = temp;
	}
	
	String getLocalIP(){
		return this.localIP;
	}
	
	String getPassword(){
		String passwordS="";
		for(int i=0;i<password.length;i++) passwordS = passwordS + password[i];
		return passwordS;
	}
	
	
}
