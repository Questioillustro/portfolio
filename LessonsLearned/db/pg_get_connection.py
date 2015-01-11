# <package>
#     LessonsLearned
# <.package>
# <description>
#     Module to create all tables for the LessonsLearned application database
# <.description>
# <keywords>
#     psycopg2, postgres, connect to database, try, except
# <.keywords>

import psycopg2
import util.debug as debug

def create_conn(dbName):
    try:
        # Admin connection
        conn = psycopg2.connect("dbname='"+dbName+"' user='postgres' host='localhost' password='create'")
    except Exception, err:
        debug.error("Unable to establish connection")
        debug.error(err.message)

    cur = conn.cursor()
    return cur, conn

def get_visitor_conn(dbName):
    try:
        # Visitor connection
        conn = psycopg2.connect("dbname='"+dbName+"' user='visitor' host='localhost' password='visitor'")
    except Exception, err:
        debug.error("Unable to establish connection")
        debug.error(err.message)

    cur = conn.cursor()
    return cur, conn
