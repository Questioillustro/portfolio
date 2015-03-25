/*
<package>
	Java GUI Histogram
<.package>
<description>
    Stores information for and Age Group, used in the histogram
<.description>
<keywords><.keywords>
*/

import java.text.*;

class AgeGroup
{
	private String ageGroup;
	private int maleNum, femaleNum;
	private double malePerc, femalePerc;
	
	public AgeGroup()
	{
		ageGroup = "NA";
		maleNum = 0;
		femaleNum = 0;
	}
	
	public AgeGroup(String g, int fNum, int mNum)
	{
		ageGroup = g;
		maleNum = mNum;
		femaleNum = fNum;
	}
	
	//Sets the percentage for the agegroup within the total population
	public void setPercent(int tot)
	{
		DecimalFormat df = new DecimalFormat("#.####");
		femalePerc = Double.valueOf(df.format(((double)femaleNum/(double)tot) * 100));
		malePerc = Double.valueOf(df.format(((double)maleNum/(double)tot) * 100));
	}
	
	public String getGroup()
	{
		return ageGroup;
	}
	
	public int getFemaleNum()
	{
		return femaleNum;
	}
	
	public int getMaleNum()
	{
		return maleNum;
	}
	
	public double getMalePerc()
	{
		return malePerc;
	}
	
	public double getFemalePerc()
	{
		return femalePerc;
	}

}//E - AgeGroup{}