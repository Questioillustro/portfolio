# <package>
#     Word Histogram in ruby
# <.package>
# <description>
#     Takes text from standard in and produces a histogram of word occurences
# <.description>
# <keywords>
#     stdin, histogram
# <.keywords>

#Generates a histogram of word occurences using stdin
#Author: Stephen Brewster
#02/16/2014
#

#Formats a line from stdin
def prepareLine(line)
  line = line.downcase
              .chomp!
              .gsub(/[^\s0-9a-zA-Z']*/,"")
              .sub(/^\s*/, "")
  puts "#{line}"
  line
end

#Adds a line of words to the hash of word occurences
def hashLine(line, histogram)
  words = line.split(/ +/)
  words.each { |word|
    #print "#{word} = #{histogram[word]+1}\n"
    if histogram.has_key?(word) then histogram[word] +=1
      else histogram[word] = 1
    end
  }
  histogram
end

#Returns an array of hashes based on a minimum value (word occurrence)
def getOccurencesWithMin(min,bag)
  bag.select { |k,v| v >= min }
end

#Sorts the hash by values, then keys when values match
def sortArray(array)
  #array.sort_by { |k,v| [v,k] } 
  array.sort { |k,v| 
  comparison = k.last <=> v.last
  if comparison.zero?
    k.first <=> v.first
  else
    comparison
  end
}
end

#Once a hash is fully generated from stdin, print results
def printWordOccurences(bag, title)
  puts "\n#{title}"
  bag.each { |k,v| print "#{k} = #{v}\n" }
end

#Print histogram where each * represents 1 occurence
def printHistogram(bag)
  #Find the length of the longest word to format print
  longestWordLen = bag.inject(0) { |c,(k,v)|
    k.length > c ? k.length : c
  }
  #Print histogram
  bag.each { |k,v|
    printf("%-#{longestWordLen}.#{longestWordLen}s%s\n", k, "*" * v)
  }
end

#Main functions
bag = Hash.new(0)

#Use integer argument from CLI to set minimum occurrence for histogram
#Default of 2
if ARGV[0] != nil then occurMin = ARGV[0].to_i 
  else occurMin = 2
end

#Iterate through lines provided to stdin and process
$stdin.each { |a|
  a = prepareLine(a)
  bag = hashLine(a, bag)
}

#printWordOccurences(bag, "Full list of word occurences")
reducedBag = getOccurencesWithMin(occurMin, bag)
#printWordOccurences(reducedBag, "Words with 2 or more occurences")

sorted = sortArray(reducedBag)
#printWordOccurences(sorted, "Sorted array")
printHistogram(sorted)

