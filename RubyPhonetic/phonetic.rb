# <package>
#     Phonetic Alphabet
# <.package>
# <description>
#     Converts text to the NATO phonetic alphabet
# <.description>
# <keywords>
#     stdin
# <.keywords>

# Convert to/from phonetic alphabet
# Author: Stephen Brewster
# 02/17/2014

class Phonetic

  Letters = [
             ['A', 'ALPHA'],
             ['B', 'BRAVO'],
             ['C', 'CHARLIE'],
             ['D', 'DELTA'],
             ['E', 'ECHO'],
             ['F', 'FOXTROT'],
             ['G', 'GOLF'],
             ['H', 'HOTEL'],
             ['I', 'INDIA'],
             ['J', 'JULIET'],
             ['K', 'KILO'],
             ['L', 'LIMA'],
             ['M', 'MIKE'],
             ['N', 'NOVEMBER'],
             ['O', 'OSCAR'],
             ['P', 'PAPA'],
             ['Q', 'QUEBEC'],
             ['R', 'ROMEO'],
             ['S', 'SIERRA'],
             ['T', 'TANGO'],
             ['U', 'UNIFORM'],
             ['V', 'VICTOR'],
             ['W', 'WHISKEY'],
             ['X', 'XRAY'],
             ['Y', 'YANKEE'],
             ['Z', 'ZULU'],
             ]

  # Translate a word to its phonetic alphabet equivalent
  def self.to_phonetic(word)
    word.gsub!(/[^a-zA-Z]/,"")
    phonetic = ""

    word.each_char do |c|
      # For each letter in the word find the Alpha to NATO Phonetic
      match =  (Letters.select { |k,v| k == c.upcase })
      # Ensure a phonetic was found to match the character
      if match.empty? then raise "Invalid character: #{c}" end
      phonetic += match.pop[1] + " "
    end

    print "#{phonetic.rstrip!}\n"
    phonetic
  end

  # Translate a sequence of phonetic alphabet code words 
  # to their alphabetic equivalent
  def self.from_phonetic(str)
    lineAsArr = str.split(/ +/)
    alpha = ""

    lineAsArr.each do |w|
      # For each word in the string find the NATO Phonetic to Alpha
      match = (Letters.select { |k,v| v == w.upcase })
      # Ensure an alpha character was found to match the given phonetic
      if match.empty? then raise "Invalid phonetic: #{w}" end
      alpha += match.pop[0]
    end

    print "#{alpha}\n"
    alpha
  end

  # Translates a string of plain text to the NATO Phonetic Alphabet if it
  # starts with 'A2P' and the reverse translation for 'P2A'
  def self.translate(trans_line)
    lineAsArr = trans_line.split(/ +/) #Convert string to array of words
    cmd = lineAsArr.shift.upcase       #Pop the command word out of the array

    # Translate Alpha to Phonetic
    if cmd == "A2P" then
      return lineAsArr.inject("") { |result,w| result += Phonetic.to_phonetic(w) }

    # Translate Phonetic to Alpha
    elsif cmd == "P2A" then
      return Phonetic.from_phonetic(lineAsArr.join(" "))

    # First word not A2P or P2A
    else
      raise "Missing command word (A2P or P2A): #{trans_line}"
    end

  rescue => detail
    print "#{detail.message}\n"
    return detail.message
  end
end

if STDIN.tty? then
  print "Not reading from STDIN\n"
else
  STDIN.each { |l| if !l.rstrip!.split(/ +/).empty? then Phonetic.translate(l) end }
end

