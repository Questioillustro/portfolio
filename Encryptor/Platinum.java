/*
<package>
    Encryptor
<.package>
<description>
    Encrypts and decrypts text using a custom shift cypher
<.description>
<keywords>
    shift cypher, encryption
<.keywords>
*/

public class Platinum extends Encryptor{
	private int[] intForm;
		
	public String encrypt (String s)
	{
		String temp = "";
		intForm = new int[s.length()];
	
		//Convert string to ASCII values
		for (int i = 0; i < s.length(); i++)
		{
			intForm[i] = s.charAt(i);
		}
		
		//Call series of encryption algorithms
		encryptShift();
		
		//Set temp string
		for (int i = 0; i < s.length(); i++)
		{	
			temp += intForm[i];
			
			if (i != s.length() - 1)
			  	temp += "-";	
		}
		return temp;
	}
	
	public String decrypt (String s)
	{
		String temp = "";
		
		//Convert string to ASCII values
		for (int i = 0; i < s.length(); i++)
		{
			intForm[i] = s.charAt(i);
		}
		
		removeDashes(s);
		decryptShift();
		
		//Set temp to string
		for (int i = 0; i < s.length(); i++)
		{		
	   	temp += (char)intForm[i];	
		}
	
		return temp;
	}
	
	private void encryptShift ( )
	{
		boolean switcher = true;
		
		for (int i = 0; i < intForm.length; i++)
		{
			if (switcher){
				intForm[i]++;
			}else{
			 	intForm[i]--;
			}
			switcher = !switcher;
		}
	} 
	
	private void decryptShift ( )
	{
		boolean switcher = true;
		
		for (int i = 0; i < intForm.length; i++)
		{
			if (switcher){
				intForm[i]--;
			}else{
			 	intForm[i]++;
			}
			switcher = !switcher;
		}
	} 
	
	private void removeDashes ( String s )
	{
		String[] splitter;
		splitter = s.split("-");
		
		for (int i = 0; i < splitter.length; i++)
		{
			intForm[i] = (int)splitter[i].charAt(0);
		}
	}
				
}