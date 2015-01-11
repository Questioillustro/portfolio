/*
<package>
	Decimal-Binary Converter
<.package>
<description>
    Converts numbers between decimal and binary
<.description>
<keywords>
	binary, converter
<.keywords>
*/

/*
	Runs With: BDNumberMain
*/
class BDNumber{
	private int[] number = new int[11];
	private String Hex;
	
	//Set D/B values when Integer is passed
	public BDNumber(String num, int base){
		switch(base)
		{
		case 2:  number[10] = convertBtoD(num);
				 Hex = convertBtoHex(num);
				 break;
		case 10: number[2] = convertDtoB(num);
				 break;
		}
		
		
	}
	
	//Returns user requested base
	public String getConversion(int b)
	{
		if (b == 16){
			return Hex;
		} else{
			return Integer.toString(number[b]);
		}
	}

	//Convert binary to decimal	
	private int convertBtoD(String n) {
		int j = 0;
		int sum = 0;
		
		for (int i = n.length() - 1; i >= 0; i--){
			if (n.charAt(i) == '1')
			{
				sum += (int) Math.pow(2.0, j);
			}
			j = j + 1;
		}
		return sum;
	}
	
	private String convertBtoHex(String n) {
		int sum = 0, i = 0;
		double segments = Math.ceil((double)(n.length()) / 4); // Finds the number of bytes required
		String retrieveByte = "";
			for (int j = 1; j <= segments; j++)
				{
				i = - (1 + 3*j)
					while (n.charAt(i) != null)
					{
						System.out.println(Character.toString(n.charAt(n.length()-1)));
					}
					//System.out.print(retrieveByte);
				}
		return Integer.toString(sum);
	}
	
	//Convert Decimal to Binary
	private int convertDtoB(String num) {
	String result = "";
	int n = Integer.parseInt(num);
	
	if (n == 0)
	{
		result = "0";
	}
	else
	{
		while (n > 0) {
			result = n%2 + result ;
			n = n / 2;
		}
	}
	return Integer.parseInt(result);
	}
	

} // End class


/*
//Addition of the 2 numbers 
public BDNumber add(BDNumber n) {
	BDNumber sum;
		sum = new BDNumber(this.getDec() + n.getDec());
return sum;
}	

//Subtraction of the 2 numbers
public BDNumber subtract(BDNumber n) {
	BDNumber subtract;
	
//Find the proper order of subtraction to avoid a negative number and set result to a new object
		if (this.dec_num >= n.dec_num)			
					subtract = new BDNumber(this.getDec() - n.getDec());
			else
					subtract = new BDNumber(n.getDec() - this.getDec());
		
	return subtract;
}
*/