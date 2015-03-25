#!/bin/bash
# Extend the following script to run tests that confirm your program is
# working correctly. You are not required to create PyUnit tests.
# 

#Testing original sample
echo "I think February 29, 2024 will be sunny." | python3 Bracket.py
# Output should be: [I] th[i]nk F[e]br[u][a]ry <2><9>, <2><0><2><4> w[i]ll b[e] s[u]nny.

#Test Lowercase Vowels Only
echo "aeiou" |  python3 Bracket.py
# Output should be: [a][e][i][o][u]

#Add others here:
#special characters
echo "& * ) + ) * ^ * [ ] > <  @ = + -" | python3 Bracket.py
#Escape characters
echo "SUPER/\n Calah\t Fraja\t lIstick\v expy al uh docious" | python3 Bracket.py
#all integers
echo "1 2 3 4 5 6 7 8 9 0 10 11 12 --- 1001 29075837270" | python3 Bracket.py
#just a long string
echo "1 for the money, 2 for the show, 3 to get ready, and 4 to go! hold on i'm still not ready! ok maybe now. nope, wait, yeah." | python3 Bracket.py
