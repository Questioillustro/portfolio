# <package>
#     Prime Numbers
# <.package>
# <description>
#     Computes prime number statistics
# <.description>
# <keywords>
#     prime numbers, math
# <.keywords>

from math import *

PRIMEGOAL=1000
final_prime=2
n=0
product=0

def is_prime( cand ):
    global n
    i = cand-1
    while( i > 1 ):
        n += 1
        if cand%i == 0:
            return False
        i -= 1
    return True

prime_count = 0
prime_cand = 1
while(prime_count < PRIMEGOAL): 
    n += 1
    if is_prime( prime_cand ):
        final_prime = prime_cand
        if product == 0:
            product = log(prime_cand)
        else:
            product = log(prime_cand) + product
        prime_count += 1
    prime_cand += 2
print PRIMEGOAL,"th prime",final_prime
print "Log sum",product
print "Log ratio",product/final_prime
print "In",n,"loops"
