class Animal
	attr_writer :name
	attr_reader :name
	attr_writer :type
	attr_reader :type
	
	def initialize(name, type)
		@name = name
		@type = type
	end 

	def callAnimal
		puts "Get over here #{@name}"
	end

	def to_s
		"A #{@type} named #{@name}"
	end
end

ob = Animal.new("Sally","Dog")
ob2 = Animal.new("Atreyu", "Cat")
ob2.name = "Falcor"
puts ob.inspect
puts ob2.inspect
