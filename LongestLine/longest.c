/*
<package>
	Longest Line
<.package>
<description>
    Scans text from stdin and prints the longest line found
<.description>
<keywords>
    stdin, strings
<.keywords>
*/

#include <stdlib.h>
#include <stdio.h>

#define MAXLINE 80   /* maximum input line size */

/* function declarations */
int readline( char line[], int max );
void copy( char to[], char from[] );

/* variable declarations */
int longest_len = 0 ;
char longest_line[MAXLINE] ;

/* print longest input line */
int main() {
    int len;    /* current line length */
    char line[MAXLINE];    /* current input line */

    while ( (len = readline( line, MAXLINE )) > 0 ) {
        //printf("%s\n", line);
        if( len > longest_len ) {
            //printf("Longer string found { old: %d , new: %d }\n", longest_len, len) ;
            longest_len = len ;
            copy(longest_line, line) ;
        }
    }
    printf("%s\n", longest_line) ;

    return 0;
}

/* readline: read a line into s, return length */
int readline( char s[], int lim ) {
    int i = 0 ;
    char ch = fgetc(stdin) ;

    while ( ch != 10 && ch != EOF && i < MAXLINE ) {
        s[i++] = ch ;
        ch = fgetc(stdin) ;
    }
    s[i] = '\0' ;

    return i;
}

/* copy: copy 'from' into 'to'; assume to is big enough */
void copy( char to[], char from[] ) {
    int i ;

    for(i = 0; i < longest_len; i++) {
        to[i] = from[i] ;
    }
    to[longest_len] = '\0' ;
}



