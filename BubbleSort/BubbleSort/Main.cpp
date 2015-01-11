/*
<package>
	BubbleSort_cpp
<.package>
<description>
	Sorts an array of random integers
<.description>
<keywords>
	bubble sort
<.keywords>
*/

#include <iostream>
#include <cstdlib>
using namespace std;

int main()
{
	int unsortedArray[100000], sortedArray[100000], x, ops(0), increment(2000), elements(1000);
	bool flag = true;

for (int j = 0; j <= 9; j++)
{
	for (int i = 0; i <= elements; i++)
	{
		unsortedArray[i] = rand() % 1000;
		sortedArray[i] = unsortedArray[i];
		//cout << unsortedArray[i] << "\t";
	}
	
	while(flag)
	{
	flag = false;
		for (int i = 0; i <= elements-1; i++)
		{
			if (sortedArray[i+1] < sortedArray[i])
			{
				ops++;
				flag = true;
				x = sortedArray[i];
				sortedArray[i] = sortedArray[i+1];
				sortedArray[i+1] = x;
			}
		}
	} //end while loop

	flag = true;			
	cout << "Elements: " << elements << "\tOperations: " << ops << "\n";
	elements += increment;
	ops = 0;

}//end j loop
	
	cout << "\nPress any key...";
	cin >> x;
}