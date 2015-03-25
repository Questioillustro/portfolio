# <package>
#     Practicum 2 - swen250
# <.package>
# <description>
#     Tests the practicum 2 methods, written in a 50 minute practicum
# <.description>
# <keywords>
#     unit test, practicum
# <.keywords>

require_relative 'currency' 
require 'test/unit'          

class CurrencyTest < Test::Unit::TestCase 

  ### Add your tests here ###

  # assert_in_delta() is used to compare two floating point
  # values within a tolerance, or delta, to account for potential
  # rounding errors when performing floating point computations.

  def test_conversion_1
    assert_in_delta 58.59, convert( 'GBP', 35.0 ), 0.01
  end

  def test_conversion_2
    assert_in_delta 1.722, convert( 'CAN', 2.0 ), 0.01
  end

  def test_line_to_CSV
    assert_equal ["+","GBP","35.0"], parse_line("+,GBP,35.0")
    assert_equal ["-","CAN","33","EUR","78"], parse_line("-,CAN,33,EUR,78")
  end

  def test_compute
    assert_equal 35.2, compute("+",20.0,15.2)
    assert_equal 40.0, compute("-",67.78,27.78)
  end
end
