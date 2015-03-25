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
#include "EasyBMP.h"
#include <math.h>
#include <cstdlib>
#include <iostream>
#include <cstring>
using namespace std;

int main()
{
	const int IMAGE_WIDTH = 1024;	//Size of final .bmp image
	const int IMAGE_HEIGHT = 828;   //"
	int x(270),y(140),w(50),mod(250);  //Variables used for manipulating position/color
	char file[20] = "redRectangle.txt";

	//Create image & set size & fill background
	BMP The_Image;	
	The_Image.SetSize(IMAGE_WIDTH, IMAGE_HEIGHT);
	fill_background(The_Image, 0, 100, 255);
	
//******** Draws the 4 blue (red outlined) gradient pattern ***************
	//Uses 2 C2DArray objects in a 10 cycle loop, modifying size, position
	//and color after each loop
	for (int i = 0; i < 10; i++)
	{
		C2DArray redRect = C2DArray(w, w, mod, 0, 0);
		C2DArray blueRect = C2DArray(w - 1, w - 1, 0, 0, mod);
		
		//paints underlying red bursts
		redRect.paint(The_Image, x - 1, y - 1);
		redRect.paint(The_Image, x, 768 - y);
		redRect.paint(The_Image, 1024 - x, y);
		redRect.paint(The_Image, 1024 - x, 768 - y);
		
		//paints blue bursts
		blueRect.paint(The_Image, x, y);
		blueRect.paint(The_Image, x + 1, 768 - y);
		blueRect.paint(The_Image, 1024 - x, y + 1);
		blueRect.paint(The_Image, 1024 - x, 768 - y);
		
		//spacing and coloring modifiers
		w -= 4;
		x += 20;
		y += 20;
		mod -= 25;
	}
	
	//Reset size, space, color modifiers 
	w = 5;
	x = 517;
	y = 350;
	mod = 0;
	
//******* Draws the Red rectangles (vertical)
	for (int i = 0; i < 6; i++)
	{
		//Creates and paints top gradient
		C2DArray greenRect = C2DArray(w, w, 0, mod, 0);
		greenRect.paint(The_Image, x, y);

		C2DArray redRect = C2DArray(w - 2, w - 2, mod, 0, 0);
		redRect.paint(The_Image, x + 1, y);

		//Paints bottom gradient
		greenRect.paint(The_Image, x, 768 - y);
		redRect.paint(The_Image, x + 1, 770 - y);
		
		//Adjust size, position, color
		w += 10;
		x -= 4;
		y -= 65;
		mod += 20;
	}

//****** Draws Center GREEN diamonds ************
	C2DArray bigGreenRect = C2DArray(150, 150, 0, 250, 0, 0, 0, 0);
	bigGreenRect.paint(The_Image, 445, 315);

	C2DArray midGreenRect = C2DArray(100, 100, 0, 125, 0, 0, 180, 0);
	midGreenRect.paint(The_Image, 470, 340);

	C2DArray smallGreenRect = C2DArray(50, 50, 0, 75, 0, 0, 125, 0);
	smallGreenRect.paint(The_Image, 495, 365);

//****** Draws Left RED diamonds *****************
	C2DArray bigRedRect = C2DArray(150, 150, 250, 0, 0, 0, 0, 0);
	bigRedRect.paint(The_Image, 295, 315);

	C2DArray midRedRect = C2DArray(100, 100, 125, 0, 0, 180, 0, 0);
	midRedRect.paint(The_Image, 320, 340);

	C2DArray smallRedRect = C2DArray(50, 50, 75, 0, 0, 125, 0, 0);
	smallRedRect.paint(The_Image, 345, 365);
	
//******* Draws Right BLUE diamonds *****************
	C2DArray bigBlueRect = C2DArray(150, 150, 0, 0, 250, 0, 0, 0);
	bigBlueRect.paint(The_Image, 595, 315);

	C2DArray midBlueRect = C2DArray(100, 100, 0, 0, 125, 0, 0, 180);
	midBlueRect.paint(The_Image, 620, 340);

	C2DArray smallBlueRect = C2DArray(50, 50, 0, 0, 75, 0, 0, 125);
	smallBlueRect.paint(The_Image, 645, 365);
	
	//utilizes the save-to-file and read-from-file utilities
	bigRedRect.saveToFile("redRectangle.txt");
	C2DArray paintFromFile = C2DArray("redRectangle.txt");
	paintFromFile.paint(The_Image, 145, 315);
	
	bigBlueRect.saveToFile("blueRectangle.txt");
	C2DArray paintFromFile2 = C2DArray("blueRectangle.txt");
	paintFromFile2.paint(The_Image, 745, 315);

	//save image to file
	The_Image.WriteToFile("the_image.bmp");

	return(0);
}



