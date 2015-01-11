#Unit test for simple_number

require_relative "simple_number"
require "test/unit"

class TestSimpleNumber < Test::Unit::TestCase
	def setup
		@num = SimpleNumber.new(2)
	end

	def teardown
		#Nothing
	end 

	def test_simple
		assert_equal(4, @num.add(2) )
		assert_equal(6, @num.multiply(3) )
	end

	def test_typecheck
		assert_raise(RuntimeError) { SimpleNumber.new('a') }
	end

	def test_failure
		assert_equal(3, @num.add(2), "Adding doesn't work")
	end
end

