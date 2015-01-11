/*
<package>
	C Project 1 - swen250
<.package>
<description>
	Executes the level 1 requirements of the project
<.description>
<keywords><.keywords>
*/

#include <stdio.h>

int execute_lvl_1 () {
    struct Element line ;
    int cmd ;
    int id ;
    char ch ;

    do {
        ch = parse_health_line(&line, &id, &cmd) ;
        if(ch == EOF) {
            break;
        }

        //Determine string for level 1 print
        char* statPrint = get_stat_string(cmd) ;

        printf("%s: %s for patient ID = %d is %.1f\n",
            line.timestamp, statPrint, id, (line.value/10.0)) ;

    } while( ch != EOF ) ;

    return 0;
}

