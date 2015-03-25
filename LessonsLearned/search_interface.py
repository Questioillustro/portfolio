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
    except Exception as err:
        debug.error("Invalid command or syntax {0}".format(err))
        print(err)

def display_all_keywords():
    results = query.get_all_keywords()
    return results

def display_all_languages():
    results = query.get_all_languages()
    return results

def display_all_packages():
    results = query.get_all_packages()
    return results
    
def search(search_on, search_term):
    if search_on == "keyword":
        results = query.find_by_keyword(search_term)
    if search_on == "code":
        results = query.find_in_code(search_term)
    if search_on == "language":
        results = query.find_by_language(search_term)        

    return results

def execute_sql_query():
    sql = ''
    while True:
        print("Type quit to leave the query tool")
        sql = raw_input("SQL> ")

        if sql == 'quit':
            break
        
        results = query.sql_query(sql, None)
        return results
        
        
    
