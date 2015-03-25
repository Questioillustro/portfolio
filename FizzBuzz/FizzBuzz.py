# <package>
#     Fizz Buzz
# <.package>
# <description>
#     Simple python exercise
# <.description>
# <keywords>
#     trivial
# <.keywords>

#Iterate over the integers 1-100 and Print 'Fizz' when
#the number is divisible by 5, 'Buzz' when it is divisible
#by 7, and 'FizzBuzz' when divisible by both, and the
#number itself for all other cases 
for num in range(1, 101):
	if num%5 == 0:
		print('Fizz')
	elif num%7 == 0:
		print('Buzz')
	elif num%7 == 0 and num%5 == 0:
		print('FizzBuzz')
	else:
		print(num)
