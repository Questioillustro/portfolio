# <package>
#     Palindrome Tester
# <.package>
# <description>
#     Tests a string coming from stdin to see if it is a palindrome
# <.description>
# <keywords>
#     stdin, text analysis
# <.keywords>

#Stephen Brewster
#Palindrome tester
import sys
import re

notAlpha = re.compile("[a-zA-Z]")

#Advances the index moving forward over the line
def advance(adv, line):
	while(notAlpha.match(line[adv]) == None):
		adv += 1
	return adv


#Retreats the index moving right to left over the line
def retreat(ret, line):
	while(notAlpha.match(line[ret]) == None):
		ret -= 1
	return ret

#Return the result of character comparison
def is_palindrome(line):
	adv = 0
	ret = len(line)-1
	isPalindrome = True
	
	while(isPalindrome and adv < ret):
		#Move cursors to next alpha character
		adv = advance(adv, line)
		ret = retreat(ret, line)
		#Test characters for equality
		isPalindrome  = line[adv].upper() in line[ret].upper()
		#Move cursor
		adv += 1
		ret -= 1
	
	return isPalindrome
        
	
#Read stdin lines to variable
lines = sys.stdin.readlines()

#Iterate lines from file/stdin
for line in lines:
	isPalindrome = is_palindrome(line)

	#Print results of the test
	if isPalindrome:
		print(line, "Result: Pass")
	else:
		print(line, "Result: Failed")
