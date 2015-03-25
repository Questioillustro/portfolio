/*
<package>
	Temperature Conversion
<.package>
<description>
    Shows F to C conversion for temps between 0 and 300 F
<.description>
<keywords>
	temperature
<.keywords>
*/

#include <stdio.h>
#include "math.h"
int main () {
    int f;
    int c;

    printf("Fahrenheit-Celcius\n");
    for(f = 0; f <= 300; f += 20) {
        c = (5.0/9.0)*(f-32);
        printf("\t%3d\t\%d\n", f, c);
    }
}
