#include "EasyBMP.h"
#include <windows.h>
#include <cstdlib>
#include <time.h>
using namespace std;




class redRect
{	
	public:
		void initialize_colors ();
		

		const int IMAGE_WIDTH = 100;
		const int IMAGE_HEIGHT = 100;

		struct aColor
		{
			int red;
			int green;
			int blue;
		};
	
	initialize_colors();
	fill_background(The_Image);

	The_Image.WriteToFile("the_image.bmp");
}
				
		

void initialize_colors ()
{
	aColor temp;
	for (int i = 0; i < 100; i++)
	{
		for (int j = 0; j < 100; j++)
		{
			if (j % 3 == 0 && i % 2 == 0)
			{
				temp.red = 100;
				temp.green = 100;
				temp.blue = 200;
			} else {
				temp.red = 200;
				temp.green = 150;
				temp.blue = 50;
			}
			colorGrid[j][i] = temp;
		}
	}
}

void fill_background(BMP& Image, int r, int g, int b)
{
	aColor temp = new aColor(r,g,b);
	for(int i = 0; i < Image.TellHeight() - 1; i++)
	{
		for(int j = 0; j < Image.TellWidth() - 1; j++)
		{
			temp = colorGrid[j][i];
			Image(j,i)->Red = temp.red;
			Image(j,i)->Green = temp.green;
			Image(j,i)->Blue = temp.blue;
		}
	}
	return;
}

		