/*
<package>
	Word Count in C
<.package>
<description>
    Counts the words, characters and lines of stdin, mimics wc in Linux
<.description>
<keywords>
    stdin
<.keywords>
*/

#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#include <unistd.h>

#define FALSE (0)
#define TRUE  (1)

int main(int argc, char *argv[]) {
    int tot_chars = 0 ; /* total characters */
    int tot_lines = 0 ; /* total lines */
    int tot_words = 0 ; /* total words */

    int ch;

    /* read character by character from stdin */
    do {
        ch = fgetc(stdin);
        tot_chars += 1;
        if(ch == 10) {
            tot_lines += 1;
        }
        if(ch == 32 || ch == 10) {
            tot_words += 1;
        }
    } while (ch != EOF); 

    printf("%d %d %d", tot_lines, tot_words, tot_chars);
    return 0;
}
