# <package>
#     Project 1 - swen250
# <.package>
# <description>
#     Utility methods for Project 1 - swen250
# <.description>
# <keywords>
#     stdin, csv, dictionary
# <.keywords>

# grades_util.rb Ruby Script
#
# Support methods used by grades.rb for reading CSV gradebook files
# and producing reports
#

##### TRANSLATION HASH FOR LETTER GRADES TO NUMERIC #####

LETTER_TO_NUMERIC = {
  "A+" => 98, "A" => 95, "A-" => 92,
  "B+" => 88, "B" => 85, "B-" => 82,
  "C+" => 78, "C" => 75, "C-" => 72,
  "D+" => 68, "D" => 65, "D-" => 62,
  "F+" => 55, "F" => 40, "F-" => 25,
}

##### TRANSLATION OF LETTER GRADES TO QUALITY POINTS #####

QUALITY_POINTS = { 'A' => 4, 'B' => 3, 'C' => 2, 'D' => 1, 'F' => 0 }

##### TRANSLATION OF NUMBERS 0-100 TO WHOLE LETTER GRADES

NUMERIC_TO_WHOLE_LETTER = [
  [(0..59),"F"],
  [(60..69),"D"],
  [(70..79),"C"],
  [(80..89),"B"],
  [(90..100),"A"]
]

# sum_weights( headers, weights)
# headers = array of header strings (1st line of input)
# weights = array of weights strings (2nd line of input)
# returns integer value equal to the sum of all weights
#
# Method to sum up the weights in the weight line. 
# Skips over empty fields.

def sum_weights(headers, weights)
  raise "Headers and Weights must be equal lenghts" unless headers.size == weights.size
  weights.inject(0) { |sum,w| sum += w.to_i }
end

# numeric_to_letter(numeric)
# numeric =  integer value to convert to a whole letter grade
# return letter grade as a string
#
# Method to convert a raw numeric grade to a whole letter A - F.
# A (90-100), B (80-89), C (70-79), D (60-69), F (<60)

def numeric_to_letter(numeric)
  raise "Invalid Input" unless  numeric.is_a? Integer and
                                numeric >= 0 and
                                numeric <= 100

  NUMERIC_TO_WHOLE_LETTER.select do |k,v|
    if k.to_a.include?(numeric) then return v end
  end
end

  
# get_CSV_line( line)
# line = string of one line of input
# returns array of strings representing fields in that line
#
# Get a CSV line for the header line, weight lines or field line
# Each line should be "chomped" to eliminate the end of line character(s).
# Create arrays for the headers, weights and fields by splitting on commas.

def get_CSV_line( line ) 
  line.chomp!
  line.rstrip!
  line.lstrip!
  line.split(",")
end

# compute_grade( weight, field )
# weight = string value representing weight of the graded item
# field = string value representing value of graded item in numeric or letter grade format
# returns floating point grade value
#
# Compute a numeric grade given the weight of the grade and either a numeric 
# score or letter score in the field. 

def compute_grade( weight, field )
  (LETTER_TO_NUMERIC.fetch(field.upcase, field).to_i * weight.to_i).to_f/100
end

# print_summary(lettercount)  - !!! DO NOT MODIFY !!!
# lettercount = hash of letter grades => count of number of students
#
# Prints summary information. First the count of the number of students for each
# letter grade and then the class GPA.

def print_summary(lettercount)
  qualty_points = 0
  grade_count = 0

  ['A', 'B', 'C', 'D', 'F'].each do | letter |
    puts "#{letter} = #{lettercount[letter]}"
    qualty_points += QUALITY_POINTS[letter] * lettercount[letter]
    grade_count += lettercount[letter]
  end

  printf "Class GPA = %1.2f", qualty_points.to_f / grade_count.to_f
  puts
end

