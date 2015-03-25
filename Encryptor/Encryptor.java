public class Encryptor {
		
	public String encrypt (String s)
	{
		String temp = "";
	
		for (int i = 0; i < s.length(); i++){
			temp = temp + (char)(int)(s.charAt(i) - 1);
		}	
		return temp;
	}
	
	public String decrypt (String s)
	{
		String temp = "";
	
		for (int i = 0; i < s.length(); i++){
			temp = temp + (char)(int)(s.charAt(i) + 1);
		}	
		return temp;
	}
}