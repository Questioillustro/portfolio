# <package>
#     Word Ladder
# <.package>
# <description>
#     Generates a word ladder between two words if it exists
# <.description>
# <keywords>
#     BFS, breadth-first search, AI
# <.keywords>

# Date: 9/9/2014
# Author: Stephen Brewster
# Synopsis: Performs a breadth-first search to find the shortest path
#     between two english words. 
# Rules:
#   .Change one letter at a time
#   .Each changed letter must produce a valid english word
#

import enchant

# Open dictionary of american english words for validation
dict = enchant.Dict("en_us")
alpha = "a b c d e f g h i j k l m n o p q r s t u v w x y z".split();
dict_map = {}
expanded = {}

#for lines in dict:
#    dict_map[lines.lower().strip("\n")] = True

# Verify word is in the dicitionary
def is_word(test_word):
    global dict
    return dict.check(test_word)

def successors( state, target ):
    bag = []
    global expanded
    # Find non-matching characters for mutation
    for i in range ( len(state)+1 ):
        # If the letter already coincides with target -> skip
        if state[i-1:i] == target[i-1:i]:
            continue
        # Otherwise store all valid words generated from changing letter
        else:
            for lett in alpha:
                test = state[:i-1] + lett + state[i:]
                if test == state:
                    continue
                if is_word(test) and not expanded.has_key(test):
                    bag.append(test)
                    expanded[test] = True
                    continue
    
    return bag

def bfs(start, target):
    # sanity check
    if not ( is_word(start) and is_word(target) ):
        return "Invalid input"
    
    global expanded
    expanded = {}
    queue = [start]
    expanded[start] = True
    path_map = {}

    # Perform BFS
    while queue[0] != target:
        state = queue.pop(0)
        s = successors( state, target )
        path_map[state] = s
        for si in s:
            if si not in queue:
                queue.append(si)

    # Extract path
    path = [target]
    while path[0] != start:
        for key in path_map:
            if path[0] in path_map[key]:
                path.insert(0, key)
        
    return path

while(True):
    start = raw_input("Enter starting word: ")
    target = raw_input("Enter target word: ")
    print bfs(start, target)
        
    
              
