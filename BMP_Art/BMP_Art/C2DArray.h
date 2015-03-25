#ifndef C2DARRAY_H
#define	C2DARRAY_H
#include "EasyBMP.h"
#include <cstring>

struct aColor
{
	int red;
	int green;
	int blue;
	int alph;
};

typedef aColor* ColArrayPointer;

class C2DArray
{
public:
	C2DArray();
	//constructs rectangle
	C2DArray(int width, int height, int red, int green, int blue);
	//constructs diamond
	C2DArray(int width, int height, int red, int green, int blue, int bgred, int bggreen, int bgblue);
	//copy constructor
	C2DArray(const C2DArray& aC2DArrayObj);
	//constructs from a file
	C2DArray(char file[]);
	~C2DArray();

	C2DArray& operator =(const C2DArray& rightSide);

	void paint(BMP& Image, int x, int y);
	void saveToFile(char file[]);
	
private:
	ColArrayPointer *theData;
	int w, h;
	int r, g, b;
};

void fill_background(BMP& Image, int r, int g, int b);

#endif