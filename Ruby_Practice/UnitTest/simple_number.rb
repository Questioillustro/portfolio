#Simple Number object from en.wikibooks.org/wiki/Ruby_Programming/Unit_testing

class SimpleNumber
	def initialize(num)
		raise unless num.is_a?(Numeric)
		@x = num
	end

	def add(y)
		@x + y
	end

	def subtract(y)
		@x - y
	end

	def multiply(y)
		@x * y
	end

	def divide(y) 
		@x / y
	end

	def exp(y)
		@x^y
	end
end
