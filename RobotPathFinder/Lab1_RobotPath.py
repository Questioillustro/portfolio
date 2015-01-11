# <package>
#     Robot Path Planner
# <.package>
# <description>
#     Uses a black and white map to calculate a path between two coordinates
# <.description>
# <keywords>
#     PIL, Queue, time, math, image, A* search, DFS, depth-first search, AI
# <.keywords>

# Author : Stephen Brewster
# Date : 9/21/2014
# Description : Using a black and white map as a resource
#    plot a path between 2 specified points using A*
#    as a search algorithm and optimize the resulting path

from PIL import Image, ImageDraw
import math
import time
import Queue

#~~~~~~~~~+++ UTILITY +++~~~~~~~~~~~~@

def draw_circle( xy, r, color ):
    global draw
    draw.ellipse( ( xy[0]-r, xy[1]-r,
                    xy[0]+r, xy[1]+r ),
                    fill = color )

def draw_line( s, e, color ):
    global draw
    draw.line( ( s, e ), fill = color)

def draw_rectangle( xy, r, color):
    global draw
    draw.rectangle( ( ( xy[0]-term_rad,xy[1]-term_rad
                     ),( xy[0]+term_rad,xy[1]+term_rad
                      ) ), fill = color, outline = color )

def draw_path( path ):
    for node in path:
        draw_rectangle( node, 1, (255,0,0) )

    save_map()

# Ensures location is valid
def valid_location( xy ):
    global pix_map
    x = xy[0]
    y = xy[1]
    
    for i in range( -1, 2 ):
        for j in range( -1, 2 ):
            if pix_map[x+i,y+j] == (0,0,0):
                return False
    return True

# generates all successors of a pixel
def get_successors( xy, simplify ):
    x = xy[0]
    y = xy[1]
    succ = []
    #succ_range = 1
    
    #if simplify:
    #    succ_range = 90

    #x_succ = get_x_succ( xy, succ_range )
    #for s in x_succ:
    #    succ.append( s )
    #    for t in get_y_succ( s, succ_range ):
    #        succ.append( t )

    #y_succ = get_y_succ( xy, succ_range )
    #for s in y_succ:
    #    succ.append( s )

    for i in range( -1, 2 ):
        for j in range( -1, 2 ):
            succ.append( ( x + i, y + j ) )

    return succ    

#def get_x_succ( xy, r ):
#    global expanded
#    succ = []
#    for i in range( 1, r+1 ):
#        c = ( xy[0] + i, xy[1] )
#        if not (c in expanded) and valid_location( c ):
#            succ.append( c )
#            expanded.append( c )
#        else:
#            break

#    for i in range( 1, r+1 ):
#        c = ( xy[0] - i, xy[1] )
#        if not (c in expanded) and valid_location( c ):
#            succ.append( c )
#            expanded.append( c )
#        else:
#            break
#    return succ

#def get_y_succ( xy, r ):
#    succ = []
#    for i in range( 1, r+1 ):
#        c = ( xy[0], xy[1] + i )
#        if not (c in expanded) and valid_location( c ):
#            succ.append( c )
#            expanded.append( c )
#        else:
#            break

#    for i in range( 1, r+1 ):
#        c = ( xy[0], xy[1] - i )
#        if not (c in expanded) and valid_location( c ):
#            succ.append( c )
#            expanded.append( c )
#        else:
#            break
#    return succ

# Prompt user for the robot's travel plans
def get_coordinates( ):
    global s_term, e_term
    coords = []
    coords.append( input( "Starting X: " ) )
    coords.append( input( "Starting Y: " ) )
    coords.append( input( "Ending X: " ) )
    coords.append( input( "Ending Y: " ) )

    s_term = ( coords[0], coords[1] )
    e_term = ( coords[2], coords[3] )

# Simple heuristic of 1x 90 degree turn
def heuristic( s, e, simplify ):
    global sqrt_2
    dx = abs( s[0] - e[0] )
    dy = abs( s[1] - e[1] )
    
    if simplify:
        return ( dx + dy )  + ((sqrt_2 - 2) * min(dx, dy))
    else:
        return dx + dy
    

def turn_penalty( parent, current, successor ):
    back_2 = parent[ current ]
    path = ( back_2, current, successor )
    if back_2 != None:
        return detect_turns( path, 0, 1)
    return 0

def save_map():
    global original, save_name
    original.save(save_name)

#~~~~~~~~~+++ SEARCH +++~~~~~~~~~~~~~@

# Direct line search, ignores pixel color
# Returns number of pixels traveled
def a_star_search( start, goal, simplify ):
    global pix_map
    frontier = Queue.PriorityQueue()
    frontier.put((0, start))
    nodes_visited = 0;

    parent = {}
    path_cost = {}

    parent[start] = None
    path_cost[start] = 0

    while not frontier.empty():
        nodes_visited += 1
        current = frontier.get()[1]
        if not valid_location( current ):
            continue
        
        if current == goal:
            break
        
        for successor in get_successors(current, simplify):
            new_cost = path_cost[current] + heuristic( current, successor, simplify )

            if simplify:
               new_cost += turn_penalty( parent, current, successor )
            
            if successor not in path_cost or new_cost < path_cost[successor]:
                path_cost[successor] = new_cost
                priority = path_cost[successor] + heuristic( successor, goal, simplify )
                frontier.put((priority, successor))
                parent[successor] = current
                #pix_map[current] = (200,200,200)

    return parent, path_cost, nodes_visited

def derive_path(parent, start, goal):
    temp = goal
    path = []
    while temp != start:
        path.insert( 0, temp )
        temp = parent[temp]

    return path

def post_simplify_path( path ):
    waypoints = []
    max_steps = 90
    steps = 1;
    
    for i in range( 0, len( path ) - 2 ):
        if detect_turns( path, i, 1 ) == 1:
            waypoints.append( path[i+1] )
            steps = 1
        if steps % max_steps == 0:
            waypoints.append( path[i] )
            steps = 1

        steps += 1
    waypoints.append( path[ len(path) - 1 ] )
    return waypoints

# Scans the path for turns and returns the number of turns in requested range i->look_f
def detect_turns( path, i, look_f ):
    # determine direction
    north_south = False
    east_west = False
    diagnol = False
    turn_count = 0

    for j in range( i, i + look_f ):
        two_bx = path[j][0]
        two_by = path[j][1]
        one_bx = path[j+1][0]
        one_by = path[j+1][1]
        curr_x = path[j+2][0]
        curr_y = path[j+2][1]
        north_south = ( two_bx == one_bx )
        east_west = (two_by == one_by )
        diagnol = not( north_south or east_west )

        # if the direction established has been altered, turn detected
        if ( north_south and curr_x != one_bx ) or ( east_west and curr_y != one_by ) or ( diagnol and ( curr_x == one_bx or curr_y == one_by ) ):
            turn_count += 1

    return turn_count


#~~~~~~~~~+++ DEFAULTS +++~~~~~~~~~~~@

original = Image.open("robotMap.png")
save_name = "solution.png"

pix_map = original.load() # map of pixels in image
draw = ImageDraw.Draw( original ) # draw to image

easy = [ ( 2100, 300 ), ( 2500, 1000 ) ]
medium = [ ( 1500, 275 ), ( 1500, 1000 ) ]
hard = [ ( 1200, 800 ), ( 1200, 500 ) ]
extreme = [ (352, 252 ), ( 3851, 1156 ) ]

path_simp_series = [ 
                    [ ( 2500, 1000 ), ( 2450, 1200 ) ],
                    [ ( 1500, 1060 ), ( 980, 1070 ) ]
                   ]

s_term = easy[0]  # Default Start
e_term = easy[1]  # Default End

s_term_c = ( 255, 0, 0 ) # start term color
e_term_c = ( 0, 0, 255 ) # end term color
path_c = ( 0, 255, 0 )   # path color

term_rad = 3             # radius of terminal markers
pixels_per_meter = 30    # scale of image
robot_width = 3          # in pixels
sqrt_2 = math.sqrt(2)

expanded = []

#~~~~~~~~~+++ MAIN +++~~~~~~~~~~~~~~~@    
#g = get_successors( (2500, 1000), True )
#for node in g:
#    pix_map[node] = (250,0,0)
#save_map()
#cmd = raw_input(">>")

while True:
    cmd = raw_input("Simple Search (y/n)(q to quit):\n>> ")
    simplify = False
    post_process = False
    
    if cmd == "n" or cmd == "N":
        opt_choice = input("Enter:\n\t1 for post processing simplification\n\t2 for A* simplification\n>> ")
        if opt_choice == 1:
            post_process = True
        elif opt_choice == 2:
            simplify = True
        else:
            continue
    
    get_coordinates()

    start = time.time()
    (parent, path_cost, nodes_visited) = a_star_search( s_term, e_term, simplify )
    end = time.time()

    path = derive_path(parent, s_term, e_term)
    
    if post_process:
        path = post_simplify_path( path )
        
    draw_path(path)
    print "Time: ", end - start
    print "Heuristic Distance: ", heuristic( s_term, e_term, simplify )
    print "Distance Traveled: ", len( path ) #path_cost[e_term]
    print "Nodes Visited: ", nodes_visited
    
    cmd = raw_input("Print path (y/n)\n>> ")
    if cmd == "y" or cmd == "Y":
        for node in path:
            print node

    # Draw terminals last to overlap path line
    draw_circle( s_term, term_rad, s_term_c )
    draw_circle( e_term, term_rad, e_term_c )

    save_map()

    













