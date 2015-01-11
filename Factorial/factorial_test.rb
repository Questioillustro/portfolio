# <package>
#     Factorials in ruby
# <.package>
# <description>
#     Tests the factorial methods
# <.description>
# <keywords>
#     unit test
# <.keywords>

# Test suite for factorial.rb

require_relative 'factorial' #We need to access our code to test
require 'test/unit'          #We need Ruby's unit testing library

class FactorialTest < Test::Unit::TestCase

  #Test methods MUST start with test_
  def test_normal
    assert_equal 24, factorial(4),"4! should be 24"
  end

  # Add many other test methods here
  def test_another_normal
    assert_equal 120, factorial(5), "5! should be 120"
  end

  # Test 0! boundary
  def test_zero
    assert_equal 1, factorial(0), "0! should be 1"
  end

  # Test -x! boundary
  def test_negative
    assert_raise(RuntimeError) { factorial(-1)}
  end

  # Test for string argument
  def test_string
    assert_raise(RuntimeError) { factorial("HelloWorld") }
  end
end
