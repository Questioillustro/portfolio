/*
<package>
	C Project 1 - swen250
<.package>
<description>
	Unused test method intended to verify the results of the main program
<.description>
<keywords>
    file, unused
<.keywords>
*/

#include <stdio.h>
#include <stdlib.h>

int main () {
    FILE *fo ;
    FILE *fe ;

    fe = fopen("lvl/lvl_2_expected_output.txt", "r") ;
    fo = fopen("log/test_log.txt", "r") ;

    char ch_test ;
    char ch_expected ;

    do {
        ch_test = fgetc(fo) ;
        ch_expected = fgetc(fe) ;

        if(ch_test == EOF || ch_expected == EOF) {
            break ;
        }

        if( ch_test == ch_expected ) {
            //printf("%c", ch_test) ;
        } else {
            printf("\nSymmetry break, testVal:%c, expectedVal:%c\n", ch_test, ch_expected) ;
            return -1 ;
        }
    } while ( ch_test != EOF ) ;

    fclose(fo);
    fclose(fe);

    //printf("All tests passed\n") ;

    return 0 ;
}
