/*
<package>
    CSV in c
<.package>
<description>
    Parse and print CSV formatted files read from standard input
<.description>
<keywords>
    csv, stdin
<.keywords>
*/

/*
 * Skeleton implementation for the activity to parse and print
 * CSV formatted files read from standard input.
 */

#include <stdlib.h>
#include <stdio.h>

/*
 * Boolean here - just so we don't have to import a text file.
 */

typedef enum { false, true } bool ;

#define MAX_FIELDS (15)/* maximum fields on a CSV input line */
#define MAX_CHARS (20) /* maximum characters in any one field */

/*
 * Just an array of characters representing a single filed.
*/

typedef char f_string[MAX_CHARS+1] ;    /* string for each field */

/*
 * A parsed CSV line, with the number of fields and upto MAX_FIELDS themselves.
*/

typedef struct {
    int nfields ;   /* 0 => end of file */
    f_string field[MAX_FIELDS] ;    /* array of strings for fields */
} csv_line ;

/*
 * Returns true iff the character 'ch' ends a field. That is, ch is end of file,
 * a comma, or a newline.
 */

bool is_end_of_field(char ch) {
    return (ch == ',') || (ch == '\n') || (ch == EOF) ;
}

/*
 * Return the minimum of two integers.
 */

int min(int x, int y) {
    return x < y ? x : y ;
}

/*
 * Read the next field from standard input. Returns the value of getchar() that
 * stopped (terminated) the field.
 */

int get_field(f_string field) {
    char ch ;
    int index ;

    //Initialize field string
    for(index = 0; index < MAX_CHARS; index++) {
        field[index] = " ";
    }

    index = 0 ;

    do {
        ch = getchar() ;
        if(is_end_of_field(ch)) {
            field[index] = '\0' ;
            return ch ;
        } else {
            field[index] = ch ;
            index += 1 ;
        }
    } while( !is_end_of_field(ch) ) ;
}

/*
 * Read in a CSV line. No error checking is done on the number of fields or
 * the size of any one field.
 * On return, the fields have been filled in (and properly NUL-terminated), and
 * nfields is the count of the number of valid fields.
 * nfields == 0 means end of file was encountered.
 */

csv_line get_line() {
    csv_line aLine ;
    aLine.nfields = 0 ;
    char ch ;

    do {
        ch = get_field(aLine.field[aLine.nfields]) ;
        if(ch == EOF) {
            return aLine ;
        }
        aLine.nfields += 1 ;
    } while(ch != 10) ;
    return aLine ;
}

/*
 * Print a CSV line, associating the header fields with the
 * data line fields.
 * The minimum of the number of fields in the header and the data
 * determines how many fields are printed.
 */

void print_csv(csv_line header, csv_line data) {
    int nfields = header.nfields ;
    int i ; 
    for(i = 0; i < nfields; i++) {
        printf("%s=%s\n", header.field[i], data.field[i]) ;
    }

    printf("\n") ;
}

/*
 * Driver - read a CSV line for the header then read and print data lines
 * until end of file.
 */

int main() {
    csv_line header ;
    csv_line current ;

    header = get_line() ;
    current = get_line() ;

    print_csv(header, current) ;

    while ( current.nfields > 0 ) {
        print_csv(header, current) ;
        current = get_line() ;
    }

    return 0 ;
}
