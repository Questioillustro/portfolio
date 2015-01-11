/*
<package>
    Encryptor
<.package>
<description>
    Encrypts and decrypts text using a fibonacci sequence shift cypher
<.description>
<keywords>
    shift cyper, fibonacci, encryption
<.keywords>
*/

public class Fibonacci extends Encryptor{
	int[] sequence = new int[12];
	
	public Fibonacci ( )
	{
		sequence[0] = 0;
		sequence[1] = 1;
		for (int i = 2; i < sequence.length; i++)
		{
			sequence[i] = sequence[i - 2] + sequence[i - 1];
		}
	}
	
	public String encrypt (String s)
	{
		String temp = "";
		int x = 0;
		for (int i = 0; i < s.length(); i++)
		{
			temp = temp + (char)((int)s.charAt(i) + sequence[x]);
			x++;
			if (x > sequence.length - 1)
				x = 0;
		}
		return temp;
	}
	
	public String decrypt (String s)
	{
		String temp = "";
		int x = 0;
		for (int i = 0; i < s.length(); i++)
		{
			temp = temp + (char)((int)s.charAt(i) - sequence[x]);
			x++;
			if (x > sequence.length - 1)
				x = 0;
		}
		return temp;
	}
}
			