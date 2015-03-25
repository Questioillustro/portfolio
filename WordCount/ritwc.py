# <package>
#     Word Count in Python
# <.package>
# <description>
#     Mimics the wc operation in Linux
# <.description>
# <keywords>
#     stdin
# <.keywords>

#Mimmicks the CLI unix command 'wc' for a text file, using python
import sys #import sys module for reading stdin

text = sys.stdin.readlines()
wordCount = 0;
charCount = 0;

#iterate through the lines provided to the script via stdin
for line in text:
	wordCount += len(line.split())
	charCount += len(line)
	
print(len(text), wordCount, charCount)
