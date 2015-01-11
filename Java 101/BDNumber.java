/*
	Runs With: BDNumberMain
*/

class BDNumber{
	int dec_num;
	String bin_num;
	
	//Set D/B values when Integer is passed
	public BDNumber(int num){
		dec_num = num;
		bin_num = this.convertDtoB(num);
	}
	
	//set D/B values when String is passed	
	public BDNumber(String num){
		bin_num = num;
		dec_num = this.convertBtoD(num);
	}
	
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
	
	//Returns the decimal value
	public int getDec(){
		return dec_num;
	}
	
	//Returns the binary value
	public String getBin(){
		return bin_num;
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
	
	//Convert Decimal to Binary
	private String convertDtoB(int n) {
	String result = "";
	
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
	return result;
	}
}