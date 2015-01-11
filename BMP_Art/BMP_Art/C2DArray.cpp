/*
<package>
    BMP Art
<.package>
<description>
    Generates a BMP image using external EasyBMP files
<.description>
<keywords>
    BMP, image, draw
<.keywords>
*/

#include "C2DArray.h"
#include <iostream>
#include <fstream>
using namespace std;

C2DArray::C2DArray()
{}

//Copy constructor
C2DArray::C2DArray(const C2DArray& aC2DArrayObject)
{
	theData = aC2DArrayObject.theData;
	w = aC2DArrayObject.w;
	h = aC2DArrayObject.h;
	r = aC2DArrayObject.r;
	g = aC2DArrayObject.g;
	b = aC2DArrayObject.b;
}

//Dynamically creates a two-dimensional array of aColor structures and assigns rgb values
//Creates basic square graphic, 5 ints as arguments
C2DArray::C2DArray(int width, int height, int red, int green, int blue)
	:w(width),h(height)
{
	ColArrayPointer *theColArray = new ColArrayPointer[h];
	
	//Create second dimension of array
	for (int i = 0; i < h; i++)
		theColArray[i] = new aColor[w];

	for (int i = 0; i < h; i++)
	{
		for (int j = 0; j < w; j++)
		{
			//Set rgb colors to the square
			theColArray[j][i].red = red;
			theColArray[j][i].green = green;
			theColArray[j][i].blue = blue;
		}
		cout << endl;
	}
	theData = theColArray;
}//end constructor

//Creates diamond graphic
//8 ints as arguments
C2DArray::C2DArray(int width, int height, int red, int green, int blue, int bgred, int bggreen, int bgblue)
	:w(width),h(height)
{
	ColArrayPointer *theColArray = new ColArrayPointer[h];
	double slope = (h/2)/(w/2);  //slope of the lines outlining the diamond shape
	
	//Create 2nd dimension of array
	for (int i = 0; i < h; i++)
		theColArray[i] = new aColor[w];

	for (int i = 0; i < h; i++)
	{
		for (int j = 0; j < w; j++)
		{
			//Corners of the square are isolated and assigned rgb values
			 if(i <= -slope * j + (h/2) || 
			   i <=  slope * j - (h/2) ||
			   i >= -slope * j + (w*1.5) ||
			   i >=  slope * j + (w/2))
			{
				theColArray[j][i].red = bgred;
				theColArray[j][i].green = bggreen;
				theColArray[j][i].blue = bgblue;
			//Central diamond shape assigned rgb values
			} else {
				theColArray[j][i].red = red;
				theColArray[j][i].green = green;
				theColArray[j][i].blue = blue;
			}
		}
		cout << endl;
	}
	theData = theColArray;
}//end constructor

//Constructs from a file
C2DArray::C2DArray(char file[])
{
	ifstream myfile;
	myfile.open(file);
	
	myfile >> w >> h;
	
	ColArrayPointer *theColArray = new ColArrayPointer[h];

	for (int i = 0; i < h; i++)
		theColArray[i] = new aColor[w];

	if(myfile.is_open())
	{
		for(int i = 0; i < h; i++)
			for(int j = 0; j < w; j++)
			{
				myfile >> theColArray[j][i].red 
					   >> theColArray[j][i].green
					   >> theColArray[j][i].blue;
			}
	}	
	theData = theColArray;
	myfile.close();
}//End constructor

//Paints a premade 2D array of rgb colors to a BMP image
//The color r = 0, g = 0, b = 0 is not painted to create 'transparency'
void C2DArray::paint(BMP& Image, int x, int y)
{
	for(int i = 0; i < h; i++)
		for(int j = 0; j < w; j++)
		{
			//No pixel information is changed if the rgb values are 0,0,0
			if(theData[j][i].red == 0 && theData[j][i].green == 0 && theData[j][i].blue == 0)
			{} 
			else{
			Image(j+x,i+y)->Red = theData[j][i].red;
			Image(j+x,i+y)->Green = theData[j][i].green;
			Image(j+x,i+y)->Blue = theData[j][i].blue;
			}
		}
}//end paint()

//Saves object data values to a text file
void C2DArray::saveToFile(char file[])
{
  ofstream myfile;
  myfile.open (file);
  myfile << w << " " << h << endl;

  for(int i = 0; i < h; i++)
		for(int j = 0; j < w; j++)
		{
			myfile << theData[j][i].red << " "
				   << theData[j][i].green  << " "
				   << theData[j][i].blue << endl;
		}

  myfile.close();
}//end savetofile()


//Fills background of an image
void fill_background(BMP& Image, int r, int g, int b)
{
	int x(0), y(0);  //Variables used to control background texture
	
	for(int i = 0; i < Image.TellHeight() - 1; i++)
	{
		for(int j = 0; j < Image.TellWidth() - 1; j++)
		{
			//Creates border around image
			if (i < 8 || j < 8 || i > Image.TellHeight() - 10 || j > Image.TellWidth() - 10)
			{
				Image(j,i)->Red = j + i;
				Image(j,i)->Green = j/10;
				Image(j,i)->Blue = j/10;
			}
			//Creates background gradiant
			else if (x > 20 || y > 20)
			{
				Image(j,i)->Red = j/10;
				Image(j,i)->Green = i/10;
				Image(j,i)->Blue = (i + j);
		    //Creates texture color
			} else {
				Image(j,i)->Red = j/10;
				Image(j,i)->Green = i/10;
				Image(j,i)->Blue = Image.TellWidth() - j + Image.TellHeight() - i;
			}
			//Handles the texture shape
			x++;
			if (x > 40)
				x = 0;
		}
		//Handles the texture shape
		y++;
		if (y > 40)
			y = 0;
	}
	return;
}//end fill_background()

//Destructor
C2DArray::~C2DArray()
{	
	delete theData, r, g, b, h, w;
}


