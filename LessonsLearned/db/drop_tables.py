# <package>
#     LessonsLearned
# <.package>
# <description>
#     Module to drop all tables in the LessonsLearned application database
# <.description>
# <keywords>
#     sql, try, except, tuples
# <.keywords>

import pg_get_connection
import util.debug as debug

def drop(dbName):
    cur, conn = pg_get_connection.create_conn(dbName)

    cur.execute("DROP TABLE package CASCADE;")
    cur.execute("DROP TABLE language CASCADE;")
    cur.execute("DROP TABLE script CASCADE;")
    cur.execute("DROP TABLE keyword CASCADE;")
    cur.execute("DROP TABLE haskeyword CASCADE;")
    cur.execute("DROP TABLE unsorted CASCADE;")
    cur.execute("DROP TABLE readme CASCADE;")
    cur.execute("DROP TABLE hasreadme CASCADE;")
    
    conn.commit()
    cur.close()
    conn.close()
