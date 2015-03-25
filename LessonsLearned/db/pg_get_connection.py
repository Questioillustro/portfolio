# <package>
#     LessonsLearned
# <.package>
# <description>
#     Module to create all tables for the LessonsLearned application database
# <.description>
# <keywords>
#     psycopg2, postgres, connect to database, try, except
# <.keywords>

import psycopg2, psycopg2.extras
import util.debug as debug

def create_conn(dbName):
    try:
        # Admin connection
        conn = psycopg2.connect("dbname='"+dbName+"' user='postgres' host='localhost' password='create'")
    except Exception as err:
        debug.error("Unable to establish connection")
        debug.error(err.message)

    cur = conn.cursor()
    return cur, conn

def create_dict_conn(dbName):
    try:
        # Admin connection
        conn = psycopg2.extras.RealDictConnection("dbname='"+dbName+"' user='postgres' host='localhost' password='create'")
    except Exception as err:
        debug.error("Unable to establish connection")
        debug.error(err.message)

    cur = conn.cursor()
    return cur, conn
    
def get_visitor_conn(dbName):
    try:
        # Visitor connection
        conn = psycopg2.connect("dbname='"+dbName+"' user='visitor' host='localhost' password='visitor'")
    except Exception as err:
        debug.error("Unable to establish connection")
        debug.error(err.message)

    cur = conn.cursor()
    return cur, conn
