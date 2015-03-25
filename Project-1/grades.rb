# <package>
#     Project 1 - swen250
# <.package>
# <description>
#     Uses a CSV list to print and convert student GPAs
# <.description>
# <keywords>
#     stdin, csv
# <.keywords>

require_relative 'grades_util'

# grades.rb Ruby Script
#
# Read the first row (column names) and second row (grade weights).
# If the weights don't sum up to 100, print an error message and exit.
# Otherwise print the column headers with their weights (empty weights simply don't print)
#
# For each student line, print the header and column for each field in the line.
# The field could be identifying information (if the weight is blank) or numeric (if the
# weight is non-negative). Grades can be numeric or letter (with optional +/-)
# Also prints final weighted numeric grade and letter grade.
#
# At end, prints a summary showing the number students for each letter grade and
# the class GPA.

# Create the hash for counting the number of occurrences of each letter grade.

lettercount = Hash.new(0)   # default count is 0.
# Initialize letter grades to 0 count
QUALITY_POINTS.each { |key,value| lettercount[key] += 0 }

# Use get_CSV_line() to get the header and weight lines.
# Each line should be "chomped" to eliminate the end of line character(s).
# Create arrays for the headers and weights by splitting on commas.

HEADER = get_CSV_line(STDIN.gets.chomp)
WEIGHTS = get_CSV_line(STDIN.gets.chomp)

# For each header, print the header and, if present, its weight.
print "Summary information for grades file\n\n"

HEADER.each_with_index do |val,index|
  if WEIGHTS[index] == "" then
    puts "#{val}"
  else
    puts "#{val} #{WEIGHTS[index]}%"
  end
end

# Use sum_weights() to check if the weights do not sum to 100, output the error message:
# "Weights add up to #{sum}, not 100" - where sum is the sum of input weights

SUM_WEIGHTS = sum_weights(HEADER,WEIGHTS)
begin
  if SUM_WEIGHTS != 100 then
    raise "\nWeights add up to #{SUM_WEIGHTS}, not 100"
  end
  rescue => detail
    puts detail.message
end

# Get each of the remaining lines, representing grade information for an individual student.
# Print the header for each column and whatever is in that column on the student grade line.
# Compute contribution of each weighted field to the overall grade using compute_grade(),
# remember to skip fields that do not have weights associated with them.
# Convert the numeric grade to a letter grade using numeric_to_letter().
# Output the final numeric and letter grade for that student and update the 
# lettercount hash that is keeping track of the number of occurrences of each letter grade
# for the class.

while line = gets
  # Print student information
  puts #Margin
  fields = get_CSV_line(line)
  fields.each_with_index { |f,index| puts "#{HEADER[index]}: #{f}" }

  # Compute final numeric grade
  finalNumericGrade = 0;
  fields.each_with_index do |f,index|
    if WEIGHTS[index] != "" then
      finalNumericGrade += compute_grade(WEIGHTS[index],f)
    end
  end

  # Compute final letter grade and print
  finalLetterGrade = numeric_to_letter(finalNumericGrade.to_i)
  print "Final Numeric Grade = #{finalNumericGrade.to_i} Letter = #{finalLetterGrade}\n"

  # Add Grade to hash
  lettercount[finalLetterGrade] += 1
end

# Now print the summary information - the number of students at each letter grade level
# and the class GPA using print_summary(). 

puts
classGPA = 0.0
students = 0
# Print the grade count for the class and the class GPA
lettercount.each do |key,value|
  puts "#{key} = #{value}" # Grade counts
  classGPA += QUALITY_POINTS[key] * value
  students += value
end
puts "Class GPA = #{classGPA/students}" # Class GPA
