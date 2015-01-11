# <package>
#     LessonsLearned
# <.package>
# <description>
#    Provides cmd line interface to portfolio database
# <.description>
# <keywords>
#     command line interface
# <.keywords>

import db.query as query
import util.debug as debug
import util.statistics as statistics

def display_menu():
    print "Commands and syntax\n"
    print "-- Simple Searching --"
    print "To search for substrings within the script text, readme file, module description or by keyword use 'search TARGET SUBSTRING'"
    print "TARGET can be one of: keyword, code, language"
    print "SUBSTRING is the search term of your choice\n"
    print "Syntax:"
    print "search keyword|code|language searchString\n"
    print "Other commands:"
    print "query - execute direct SQL queries"
    print "exit - leave the application"
    print "keywords - view list of keywords"
    print "languages - view list of programming languages"
    print "packages - view list of all software packages"
    print "stats - view all stats over the database"
    

def parse_command(command):
    try:
        splitter = command.split(' ')
        cmd = splitter[0]
        
        if cmd == 'search':
            search(splitter[1], splitter[2])
        elif cmd == 'keywords':
            display_all_keywords()
        elif cmd == 'packages':
            display_all_packages()
        elif cmd == 'languages':
            display_all_languages()
        elif cmd == 'help':
            display_menu()
        elif cmd == 'query':
            execute_sql_query()
        elif cmd == 'stats':
            statistics.generate_all_stats()
        else:
            debug.error("Command not recognized: " + cmd)
    except Exception, err:
        debug.error("Invalid command or syntax: " + err.message)
        print err
    
def exiting():
    print "Exiting...\n"

def display_all_keywords():
    results = query.get_all_keywords()
    display(results)

def display_all_languages():
    results = query.get_all_languages()
    display(results)

def display_all_packages():
    results = query.get_all_packages()
    display(results)
    
def search(search_on, search_term):
    if search_on == "keyword":
        results = query.find_by_keyword(search_term)
    if search_on == "code":
        results = query.find_in_code(search_term)
    if search_on == "language":
        results = query.find_by_language(search_term)        

    display(results)

def execute_sql_query():
    sql = ''
    while True:
        print "Type quit to leave the query tool"
        sql = raw_input("SQL> ")

        if sql == 'quit':
            break
        
        results = query.sql_query(sql, None)
        display(results)        
        
def display(results):
    for result in results:
        line = ''
        for index in range(len(result)):
            line += str(result[index]) + " "
        print line

cmd = ''

print "Type help"

while cmd != "exit":
    cmd = raw_input(">> ")
    parse_command(cmd)    
        
    
