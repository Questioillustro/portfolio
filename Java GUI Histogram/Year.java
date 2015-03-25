/*
<package>
	Java GUI Histogram
<.package>
<description>
    Utility functions for displaying the histogram
<.description>
<keywords>
    file, parser
<.keywords>
*/


import java.io.*;

class Year
{
	private AgeGroup fullYear[];
	private int Year_Total_Pop = 0;
	private String groupLabels[] = {"  0-4",
											  "  5-9",
											  "10-14",
											  "15-19",
											  "20-24",
											  "25-29",
											  "30-34",
											  "35-39",
											  "40-44",
											  "45-49",
											  "50-54",
											  "55-59",
											  "60-64",
											  "65-69",
											  "70-74",
											  "75-79",
											  "  80+",
											  "80-84",
											  "85-89",
											  "90-94",
											  "95-99",
											  " 100+"};
	
	public Year(String yr) throws IOException
	{
		String femaleline, maleline;

			FileReader theFile = new FileReader(yr);
			BufferedReader readIn = new BufferedReader(theFile);
			
			femaleline = readIn.readLine();
			maleline = readIn.readLine();
			
			String femSplit[] = femaleline.split("\\s");
			String maleSplit[] = maleline.split("\\s");
			
			fullYear = new AgeGroup[femSplit.length];
			
			for(int i = 0; i < femSplit.length; i++)
			{
				fullYear[i] = new AgeGroup(groupLabels[i], 
													Integer.parseInt(femSplit[i]),
													Integer.parseInt(maleSplit[i]));
				
				Year_Total_Pop += fullYear[i].getMaleNum() + 
										fullYear[i].getFemaleNum();

			}
			calcPercent();
	}//E - Year()
	
   //Generates full graph for the year
	public String printGraph(String year)
	{
		//Print hearding
		String ftemp = "", mtemp = "", fullgraph = "";
		fullgraph += ("   +++++ Census data processed from: " + year + ".txt" + " +++++\n\n");
		fullgraph += ("\t\t  Female | Male\n");
		
		for(int i = 0; i < 22; i++)
		{
			fullgraph += (groupLabels[i]); //Prints age bracket
			
			//Loops through percentage range and builds % bars for both genders
			for(double x = 4.0; x > 0.0; x -= 0.2)
			{
				if(fullYear[i].getFemalePerc() > (x - .2))	//female
					ftemp += "*";
				else
					ftemp += " ";	
					
				if(fullYear[i].getMalePerc() > (4.0 - x)) //male
					mtemp += "*";
				else
					mtemp += " ";	
			}//E - for(x)

			fullgraph += (ftemp + "|" + mtemp + "\n"); //Print bars for both genders
			
			//Reset bar strings for both genders
			ftemp = "";
			mtemp = "";	
		}//for(i)
		
		//Print bottom scale
		fullgraph += ("     ");
	
		for(int i = 0; i < 4; i++) //Female side
			fullgraph += ("+----");
		
		fullgraph += ("+"); //0 marker
			
		for(int i = 0; i < 4; i++) //Male side
			fullgraph += ("----+");
			
		fullgraph += ("\n  %  "); //Axis Label
				
		for(int i = 4; i > -5; i--) //Numerical Label
			fullgraph += (Math.abs(i) + "    "); 
		
		return fullgraph;
	}//printGraph()
	
	//Generates a pure data printout of the year
	public String printYear(String yr)
	{
		String fullgraph = "";
		fullgraph += "\t+++++ Data extracted from: " + yr + ".txt" + " +++++\n\n";
		int temp = 0;
		for(int i = 0; i < fullYear.length; i++)
		{
			temp = fullgraph.length(); //Used in aligning female column

			//Adds male stats to String
			fullgraph += (fullYear[i].getGroup() + " " + 
							  "Male = " + fullYear[i].getMaleNum() + ", " +
							  fullYear[i].getMalePerc() + "%");
	
				//Alligns Female column left justified
				for(int j = 0; j < (29 - ((fullgraph.length() - j) - temp)); j++)
				{
					fullgraph += " ";
				}

			//Adds female stats to String
			fullgraph += ("Female = " + fullYear[i].getFemaleNum() + ", " + 
							  fullYear[i].getFemalePerc() + "%\n");
							  
		}//for(i)
		
		fullgraph += ("\nTotal Pop = " + Year_Total_Pop + " (thousands)");
		
		return fullgraph;
	}//E - printYear()
	
	//Calculates and sets the percentage of that group within the total population
	public void calcPercent()
	{
		for(int i = 0; i < 22; i++)
		{
			fullYear[i].setPercent(Year_Total_Pop);
		}	
	}//calcPercent()
	

}//Year{}