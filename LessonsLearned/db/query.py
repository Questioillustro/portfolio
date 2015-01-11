# <package>
#     LessonsLearned
# <.package>
# <description>
#     Module to query the database for content
# <.description>
# <keywords>
#     psycopg2, postgresql, database, try, except, sql
# <.keywords>

import pg_get_connection
import util.debug as debug

def find_by_keyword(search):
    cur, conn = pg_get_connection.get_visitor_conn("portfolio")
    search = '%'+search+'%'

    try:
        cur.execute("SELECT s.path||'\\'||s.name FROM script as s, keyword as k, haskeyword as hk WHERE k.keyword ILIKE %s AND k.id = hk.keywordid AND s.id = hk.scriptid;",
                    [search])
        results = cur.fetchall()
    except Exception, err:
        debug.error("Keyword lookup failed: " + err.message)

    cur.close()
    conn.close()

    return results

def get_all_keywords():
    cur, conn = pg_get_connection.get_visitor_conn("portfolio")

    try:
        cur.execute("SELECT keyword FROM keyword;")
        results = cur.fetchall()
    except Exception, err:
        debug.error("All keywords lookup failed: " + err.message)

    cur.close()
    conn.close()

    return results

def find_by_language(search):
    cur, conn = pg_get_connection.get_visitor_conn("portfolio")
    
    try:
        cur.execute("SELECT s.path||'\\'||s.name FROM script as s, language as l WHERE l.name ILIKE %s AND l.id = s.languageid;",
                    [search])
        results = cur.fetchall()
    except Exception, err:
        debug.error("Language lookup failed: " + err.message)

    cur.close()
    conn.close()

    return results

def get_all_languages():
    cur, conn = pg_get_connection.get_visitor_conn("portfolio")

    try:
        cur.execute("SELECT name FROM language;")
        results = cur.fetchall()
    except Exception, err:
        debug.error("All languages lookup failed: " + err.message)

    cur.close()
    conn.close()

    return results

def get_all_packages():
    cur, conn = pg_get_connection.get_visitor_conn("portfolio")

    try:
        cur.execute("SELECT name FROM package;")
        results = cur.fetchall()
    except Exception, err:
        debug.error("All packages lookup failed: " + err.message)

    cur.close()
    conn.close()

    return results

def find_in_code(search):
    cur, conn = pg_get_connection.get_visitor_conn("portfolio")
    search = '%'+search+'%'
    
    try:
        cur.execute("SELECT s.path||'\\'||s.name FROM script as s WHERE s.content ILIKE %s;",
                    [search])
        results = cur.fetchall()
    except Exception, err:
        debug.error("Code lookup failed: " + err.message)

    cur.close()
    conn.close()

    return results

# args is the tuple argument list
def sql_query(query, args):
    cur, conn = pg_get_connection.get_visitor_conn("portfolio")
    
    try:
        cur.execute(query, args)
        results = cur.fetchall()
    except Exception, err:
        debug.error("Query failed: " + err.message)

    cur.close()
    conn.close()

    return results
    
