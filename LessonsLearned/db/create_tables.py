# <package>
#     LessonsLearned
# <.package>
# <description>
#     Module to create all tables in the LessonsLearned application database
# <.description>
# <keywords>
#     sql, try, except, tuple
# <.keywords>

import pg_get_connection
import util.debug as debug

def create(dbName):
    cur, conn = pg_get_connection.create_conn(dbName)

    cur.execute("CREATE TABLE script(id serial NOT NULL, last_modified timestamp NOT NULL, created_on timestamp NOT NULL, name character varying NOT NULL, description character varying, content character varying NOT NULL, path character varying NOT NULL, packageid integer NOT NULL, languageid integer NOT NULL, primary key(id))")
    cur.execute("CREATE TABLE keyword(id serial NOT NULL, keyword character varying UNIQUE NOT NULL, primary key(id))")
    cur.execute("CREATE TABLE haskeyword(scriptId integer NOT NULL, keywordId integer NOT NULL, primary key(scriptId, keywordId))")
    cur.execute("CREATE TABLE package(id serial NOT NULL, name character varying NOT NULL, primary key(id))")
    cur.execute("CREATE TABLE unsorted(id serial NOT NULL, file character varying NOT NULL, primary key(id))")
    cur.execute("CREATE TABLE readme(id serial NOT NULL, path character varying NOT NULL, content character varying, primary key(id))")
    cur.execute("CREATE TABLE hasreadme(readmeid integer NOT NULL, packageid integer NOT NULL, primary key(readmeid, packageid))")
    cur.execute("CREATE TABLE language(id serial NOT NULL, name character varying NOT NULL, primary key(id))")
    
    conn.commit()
    cur.close()
    conn.close()





    
