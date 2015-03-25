/*
<package>
	C Project 2 - swen250
<.package>
<description>
    Parse CSV lines and store medical patient related data in structures, check patients in and out
<.description>
<keywords>
    csv, stdin, circular buffer, linked list
<.keywords>
*/

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "health.h"

/*
* Main function for the Health Monitoring System. Primarily responsible for
* processing input of csv lines and printing as required. Data structures are
* maintained using the helper functions in health_util.c
* 
*/
#define HR "-----------------------------------------"

Chartptr patientList = NULL;    /* Define global variable patientList (declared in health.h) */
                                /* patientList is globaaly accessible in health_util.c    */

void main(){

printf("Welcome to the Health Monitoring System\n\n");

/*
*  YOUR CODE GOES HERE:
*  (1) Read an input csv line from stdin 
*  (2) Parse csv line into appropriate fields
*  (3) Take action based on input type:
*        - Check-in or check-out a patient with a given ID
*        - Add a new health data type for a given patient
*        - Store health data in patient record or print if requested
*  (4) Continue (1)-(3) until EOF
*/
    // init variables
    struct Element *element = (struct Element*)malloc( sizeof( struct Element) ) ;
    int *patientid = (int *)malloc( sizeof( int ) ) ;
    int *cmd = (int *)malloc( sizeof( int ) );
    char ch ;

    // read in first line 
    ch = parse_line(element, patientid, cmd) ;

    // primary loop to read in all data from stdin
    // parses each line, executes the command as it is processed
    while(ch != EOF) {
        switch(*cmd) {
            case 0:
                //printf("Delete data\n");
                break;
            case 1 ... 5:
                //printf("Add data point\n");
                break;
            case 6:
                //printf("Print health data for ID = %d\n", *patientid);
                break;
            case 7:
                printf("%s\n", HR);
                printf("%s: Patient ID = %d checking in\n", element->timestamp, *patientid);
                printf("%s\n", HR);
                addPatient(*patientid);
                break;
            case 8:
                //printf("%s\n", HR);
                //printf("%s: Patient ID = %d checking out\n", element->timestamp, *patientid);
                //printf("%s\n", HR);
                //removePatient(*patientid);
                break;
            case 9:
                addHealthType(*patientid, element->value);
                //printf("Added new health type: %d, for patient ID = %d\n", element->value, *patientid);
                break;
            default:
                printf("Unknown cmd\n");
                break;
        }

        // parse next line
        ch = parse_line(element, patientid, cmd) ;
    }

    printf("\nEnd of Input\n") ;
}
