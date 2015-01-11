/*
<package>
	C Project 1 - swen250
<.package>
<description>
    Utility methods for C Project 1
<.description>
<keywords>
    csv, stdin, pointers
<.keywords>
*/

/*
 * Utilities for health.c
 *
*/

/* Parses a line and returns the end char to test for EOF */

char parse_health_line(struct Element *line, int *id, int *cmd) {
    char ch ;
    // read in patient id
    scanf("%d", id) ;
    ch = getchar() ;

    // read in timestamp, null terminate it and get comma
    scanf("%8c", line->timestamp) ;
    line->timestamp[8] = '\0' ;
    ch = getchar() ;

    // read in cmd code and comma
    scanf("%d", cmd) ;
    ch = getchar() ;

    // read in value and end character
    scanf("%d", &line->value) ;
    ch = getchar() ;

    return ch ;
}

/** Prints the health data for the chart in the order given **/

void print_health_data(struct Chart chart) {
    printf("%s\n", HR) ;
    printf("Readings for Patient ID = %d are:\n", chart.id) ;

    // Loop through each type and print the readings stored
    int i, j ;
    for(i = 0; i < MAXTYPES; i++) {
        int end = chart.buffer[i].end ;
        int start = chart.buffer[i].start ;
        char* statPrint = get_stat_string(i+1) ;

        // heading for the stat
        printf("%s:\n", statPrint) ;

        // start == end only when no readings were given
        if(start == end) {
            printf("%s\n", NONE) ;
        }

        // wrap through the buffer and print the readings
        for( j = start; j != end; j=(j+1)%MAXREADINGS ) {
            char* timestamp = chart.buffer[i].reading[j].timestamp ;
            int value = chart.buffer[i].reading[j].value ;
            if(i == 0) {
                printf("%s: %.1f\n", timestamp, (value/10.)) ;
            } else {
                printf("%s: %d\n", timestamp, value) ;
            }
        }
    }
    printf("%s\n", HR) ;
}


/* Determines the function of the command */

char* get_stat_string(int cmd) {
    char* statPrint = "Default" ;
    switch(cmd) {
        case 1:
            statPrint = "Temperature\0" ;
            break ;
        case 2:
            statPrint = "Heart Rate\0" ;
            break ;
        case 3:
            statPrint = "Systolic Pressure\0" ;
            break ;
        case 4:
            statPrint = "Diastolic Pressure\0" ;
            break ;
        case 5:
            statPrint = "Respirate Rate\0" ;
            break ;
        case 6:
            statPrint = "Print\0" ;
            break ;
    }

    return statPrint ;
}

/* Initializes a chart to default values */

void initialize_chart(struct Chart *chart) {
    int i, j ;
    for(i = 0; i < MAXTYPES; i++) {
        chart->buffer[i].start = 0 ;
        chart->buffer[i].end = 0 ;
    }
}


