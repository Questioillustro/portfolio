# <package>
#     Phonetic Alphabet
# <.package>
# <description>
#     Unit tests for phonetic alphabet
# <.description>
# <keywords>
#     unit test
# <.keywords>

require_relative 'phonetic'
require 'test/unit'

class PhoneticTest < Test::Unit::TestCase

  def test_rit_to_phonetic
    assert_equal 'ROMEO INDIA TANGO', Phonetic.to_phonetic('RIT')
  end

  def test_line_rit_to_phonetic
    assert_equal 'ROMEO INDIA TANGO', Phonetic.translate('A2P RIT')
  end

  #Remove this line and place more tests here

  # Translation missing  command arg
  def test_line_missing_cmd
    alpha = 'RIT'
    assert_equal "Missing command word (A2P or P2A): #{alpha}", Phonetic.translate(alpha)
  end

  # Invalid Phonetics are not recognized
  def test_p2a_not_found
    alpha = "FOXMART ALFA INDIGO LARGE"
    assert_raise(RuntimeError) { Phonetic.from_phonetic(alpha) }
    assert_equal "Invalid phonetic: FOXMART", Phonetic.translate("P2A " + alpha)
  end

  # Non-alpha characters are not processed
  def test_non_alpha
    assert_equal 'MIKE INDIA TANGO', Phonetic.to_phonetic('<~#~@MIT!~#~>')
    assert_equal 'MIKE INDIA TANGO', Phonetic.translate('A2P &*(MIT)#@(#*@')
  end

  # Test case-sensitivity
  def test_case_sensitive
    assert_equal 'ASCEND', Phonetic.from_phonetic('alpha Sierra CHARliE eChO NoVemBer dELTa')
    assert_equal 'ALPHA SIERRA CHARLIE ECHO NOVEMBER DELTA', Phonetic.to_phonetic('aScEnD')
    assert_equal 'ASCEND', Phonetic.translate('p2a alpha Sierra CHARliE eChO NoVemBer dELTa')
  end
end
