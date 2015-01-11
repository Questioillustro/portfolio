/*
<package>
	Pointer Activity
<.package>
<description>
    Reads data from pointers
<.description>
<keywords>
    pointers
<.keywords>
*/

/*
 * Implementation of the read_data module.
 *
 * See read_data.h for a description of the read_data function's
 * specification.
 *
 * Note that the parameter declarations in this module must be
 * compatible with those in the read_data.h header file.
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "read_data.h"

void read_data(char *aChar, int *aInt, double *aDouble) {
    char intString[MAXDIGITS] ;
    char doubleString[MAXDIGITS] ;
    char ch ;
    int index = 0 ;

    //Get first character
    *aChar = getchar() ;
    getchar() ;

    //Get integer
    ch = getchar() ;
    while(ch != 36) {
        intString[index] = ch ;
        ch = getchar() ;
        index++ ;
    }
    intString[index] = '\0' ;
    *aInt = atoi(intString) ;

    index = 0 ;

    //Get double
    ch = getchar() ;
    while(ch != 36) {
        doubleString[index] = ch ;
        ch = getchar() ;
        index++ ;
    }
    doubleString[index] = '\0';
    *aDouble = atof(doubleString) ;
}
