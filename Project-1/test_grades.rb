# <package>
#     Project 1 - swen250
# <.package>
# <description>
#     Unit tests for Project 1 - swen250
# <.description>
# <keywords>
#     unit test
# <.keywords>

require_relative 'grades_util'
require 'test/unit'

# TetsGrades
#
# Unit test suite for testing grades_util.rb support methods:
# compute_grade()
# get_CSV_line()
# numeric_to_letter()
# sum_weights()

class TestGrades < Test::Unit::TestCase

  # Tests get_CSV_line to insure the entered input line
  # is correctly be parsed to an array of string words
  #
  def test_header_line
    headers = get_CSV_line("Name,ID,Grade")# pass in an input string 
    assert_equal headers, ["Name","ID","Grade"] # return an array of header words
    assert_equal ["","","100"], get_CSV_line(",,100")
  end
  
  ####  YOUR ADDITIONAL UNIT TESTS START HERE  ####
  def test_numeric_to_letter
    assert_equal "A", numeric_to_letter(90)
    assert_equal "B", numeric_to_letter(89)
    assert_equal "C", numeric_to_letter(79)
    assert_equal "D", numeric_to_letter(69)
    assert_equal "F", numeric_to_letter(59)
    assert_raise(RuntimeError) { numeric_to_letter(101) }
    assert_raise(RuntimeError) { numeric_to_letter(-1) }
    assert_raise(RuntimeError) { numeric_to_letter("NaN") }
  end

  def test_sum_weights
    assert_equal 100, sum_weights(["h1","h2","h3"],["50","30","20"])
    assert_equal 100, sum_weights(["h1","h2","h3"],["","","100"])
    # header.size != weight.size - these must be the same length
    assert_raise(RuntimeError) { sum_weights(["h1","h2"],["100"]) }
  end

  def test_compute_grade
    # Check letter grade provided
    assert_equal 85, compute_grade("100","B")
    assert_equal 98, compute_grade("100","A+")
    assert_equal 21.6, compute_grade("30","C-")
    assert_equal 45.5, compute_grade("70","D")
    # Check numeric grade provided
    assert_equal 21.6, compute_grade("30","72")
    assert_equal 45.5, compute_grade("70","65")
  end
end
  
