# <package>
#     Git Metrics
# <.package>
# <description>
#     Perform metric analysis on a git log file
# <.description>
# <keywords>
#     git, metric analysis
# <.keywords>

# Script that obtains various git metrics from a basic git log file
require "date"

# Given an array of git log lines, count the number of commits in the log
def num_commits(lines)
  commits = 0
  lines.each do |l|
    if l.start_with?("commit") then commits += 1 end
  end
  commits
end

# Given an array of git log lines, count the number of different authors
#   (Don't double-count!)
def num_developers(lines)
  authors = []
  lines.each { |l| authors.push(l.scan(/<.*>/)) if l.start_with?("Author:") }
  authors.uniq!
  authors.size
end

# Given an array of Git log lines, compute the number of days this was in development
# Note: you cannot assume any order of commits (e.g. you cannot assume that the newest commit is first).
def days_of_development(lines)
  dates = []
  lines.each do |l|
    dates.push Date.parse(l.gsub(/Date: +/,"")) if l.start_with?("Date:")
  end
  dates.sort!
  (dates.last - dates.first).to_i
end

# This is ruby idiom that allows us to use both unit testing and command line processing
# Does not get run when we use unit testing, e.g. ruby phonetic_test.rb
# These commands will invoke this code with our test data: 
#    ruby git_metrics.rb < ruby-progressbar-short.txt
#    ruby git_metrics.rb < ruby-progressbar-full.txt
if __FILE__ == $PROGRAM_NAME
  lines = []
  $stdin.each { |line| lines << line }
  puts "Number of commits: #{num_commits lines}"
  puts "Number of developers: #{num_developers lines}"
  puts "Number of days in development: #{days_of_development lines}"
end

