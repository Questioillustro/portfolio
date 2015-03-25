/*
	Program: VignereCipher
	Authors: Stephen Brewster, Win Herne, Andy Bitry
	Date: 11/9/2010
	Purpose: This program will accept a user-input sentence, then encode and decode it
	using a 'key' word.
*/

import java.util.*;

class VignereCipher{

	public static void main(String[] args){
			VignereEncoder v1 = new VignereEncoder();
			Scanner keyIn = new Scanner(System.in);
					
			String keyWord = "COFFEE", phrase;
			System.out.print("Enter a sentence: ");
			phrase = keyIn.nextLine();
			
			char[] encodedPhrase, decodedPhrase;
			int[] keyArray = {2,14,5,5,4,4};
			
				v1.setPhraseArray(phrase);
				v1.getEncoderArray(phrase,keyArray);
				
			encodedPhrase = v1.getEncodedPhrase(phrase);
			decodedPhrase = v1.getDecodedPhrase(phrase);
			
			System.out.println(encodedPhrase);
			System.out.println(decodedPhrase);
		
	}
}