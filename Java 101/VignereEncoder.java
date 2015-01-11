/*
	Runs with VignereCipher
*/

class VignereEncoder{
	int[] encoderArray;
	int[] phraseArray;
	char[] encodedPhrase;
	char[] decodedPhrase;
	
	public void getEncoderArray(String s, int[] keyArray){
	int length = s.length();
	encoderArray = new int[length];  
	int j = 0;
		for (int i = 0; i <= length - 1; i++){
			 encoderArray[i] = keyArray[j];
		j++;
		if (j > 5){
			j = 0;	
		}	
	
	}
	
	}	
	
	public void setPhraseArray(String s){
	int length = s.length();
	phraseArray = new int[length];
	
		for (int i = 0; i <= length - 1; i++){
			phraseArray[i] = (int)s.charAt(i);
		}	
	
	}
	
	public char[] getEncodedPhrase(String s){
	encodedPhrase = new char[s.length()];
		for (int i = 0; i <= s.length() - 1; i++){
			encodedPhrase[i] = (char)(phraseArray[i] + encoderArray[i]);
			//System.out.print(encodedPhrase[i]);
		}
	return encodedPhrase;
	}
	
	public char[] getDecodedPhrase(String s){
	decodedPhrase = new char[s.length()];
		for (int i = 0; i <= s.length() - 1; i++){
			decodedPhrase[i] = (char)((int)encodedPhrase[i] - encoderArray[i]);
			//System.out.print(decodedPhrase[i]);
		}
	return decodedPhrase;
	}
	
			
	
}	
	
