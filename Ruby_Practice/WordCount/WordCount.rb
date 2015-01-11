#Mimmicks the wc linux command 
wordCount=0
charCount=0
lines = ARGF.readlines

lines.each { |line| 
	wordCount += line.split.size
	charCount += line.size
}

print " #{lines.size} #{wordCount} #{charCount}\n"

