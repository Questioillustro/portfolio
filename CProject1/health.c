/*
<package>
	C Project 1 - swen250
<.package>
<description>
    Primary class for the Health Monitor System (C Project 1 - swen250)
<.description>
<keywords>
    csv, stdin, pointers
<.keywords>
*/

/*
* Health Monitoring System
*/
#include <stdio.h>
#include <ctype.h>
#include "header.h"
#include "util.c"
#include "lvl/lvl_1.c"
#include "lvl/lvl_2.c"
#include "lvl/lvl_3.c"
#include "lvl/lvl_4.c"
#include "lvl/lvl_5.c"

/*Health records for all patients defined here.
* The variable record is visible to all functions
* in this file, i.e. it is global.
*/
Chart record[MAXPATIENTS];	

void main(int argc, char **argv){
    int i, j;

    /* initialize health data records for each patient */

    for( i=0; i < MAXPATIENTS; i++ ){
        record[i].id = i + 1;
        for( j=0; j < MAXTYPES; j++ ){
            record[i].buffer[j].start = 0;
            record[i].buffer[j].end = 0;
        }
    }

    printf("Welcome to the Health Monitoring System\n\n");

/*
*  YOUR CODE GOES HERE:
*  (1) Read a csv line of health data from stdin 
*  (2) Parse csv line into appropriate fields
*  (3) Store health data in patient record or print if requested
*  (4) Continue (1)-(3) until EOF
*/
    //Determine level to execute using option
    int c ;
    while((c = getopt(argc, argv, "12345")) != -1 ) {
        switch(c) {
            case 49:
                execute_lvl_1() ;
                break ;
            case 50:
                execute_lvl_2() ;
                break ;
            case 51:
                execute_lvl_3() ;
                break ;
            case 52:
                execute_lvl_4() ;
                break ;
            case 53:
                execute_lvl_5() ;
                break ;
            default:
                break ;
        }
    }

    /* If health is run directly and no arguments are passed:
     * execute highest level
    */
    if(argc == 1) {
        execute_lvl_5() ;
    }

    printf("\nEnd of Input\n");
}
