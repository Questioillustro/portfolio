# Transform the 'original' string into the 'bracketed' string by
# surrounding all vowels - a, e, i, o, u in either case - by square
# <package>
#     Practicum 1 - swen250
# <.package>
# <description>
#     Performs some basic text manipulation, written in a 50 minute practicum
# <.description>
# <keywords>
#     text manipulation, practicum
# <.keywords>

# brackets ([]), any digit by angle brackets (<>), and copying all
# other characters "as is".
#
# When done, return the 'bracketed' string.
#
# Example: bracket("I think February 29, 2024 will be sunny.")
#    returns "[I] th[i]nk F[e]br[u][a]ry <2><9>, <2><0><2><4> w[i]ll b[e] s[u]nny."

import fileinput
import sys

lines = sys.stdin.readlines()
vowels = {"a", "e", "i", "o", "u"}
integers = {"1","2","3","4","5","6","7","8","9","0"}

def bracket(original):
	# The final bracketed string.
	bracketed = ''
	length = len(original)
	
	for index in range(0, length):
		ch = original[index]
		if ch.lower() in vowels:
			bracketed += "["+ch+"]"
		elif ch in integers:
			bracketed += "<"+ch+">"
		else:
			bracketed += ch

	return bracketed

if __name__ == '__main__':
	for line in lines:
		line = line.replace("\n", "")
		print(bracket(line))




