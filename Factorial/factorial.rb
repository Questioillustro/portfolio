# <package>
#     Factorials in ruby
# <.package>
# <description>
#     Computes factorials
# <.description>
# <keywords>
#     exceptions
# <.keywords>

# factorial(n) is defined as n*n-1*n-2..1 for n > 0
# factorial(n) is 1 for n=0
# Let's raise an exception if factorial is negative
# Let's raise an exception if factorial is anything but an integer

# Compute the value of a factorial
def factorial(n)
  # Raise errors on strings and negative numbers
  if n.is_a? String then raise "String Argument not allowed!" end
  if n < 0 then raise "Negative Argument not allowed!" end

  # Compute factorial
  (1..n).inject(1) { |product,num| product * num }
end
