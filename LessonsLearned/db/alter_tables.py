# <package>
#     LessonsLearned
# <.package>
# <description>
#     Module to set the constraints, permissions and any other features of the portfolio database
# <.description>
# <keywords>
#     dml, alter tables, permissions, constraints, database
# <.keywords>

import pg_get_connection
import util.debug as debug

def set_permissions():
    cur, conn = pg_get_connection.create_conn("portfolio")
    
    try:
        cur.execute("GRANT SELECT ON ALL TABLES IN SCHEMA public TO visitor;")
        conn.commit()
    except Exception, err:
        debug.error(err.message)

    cur.close()
    conn.close()

def create_roles():
    cur, conn = pg_get_connection.create_conn("portfolio")
    
    try:
        cur.execute("CREATE ROLE visitor;")
        conn.commit()
    except Exception, err:
        debug.error(err.message)

    cur.close()
    conn.close()

def set_constraints():
    cur, conn = pg_get_connection.create_conn("portfolio")
    
    try:
        # Script Constraints ---
        # Language foreign key
        cur.execute("ALTER TABLE script ADD CONSTRAINT fk_language FOREIGN KEY (languageid) REFERENCES language(id);")
        # Package foreign key
        cur.execute("ALTER TABLE script ADD CONSTRAINT fk_package FOREIGN KEY (packageid) REFERENCES package(id);")

        # Haskeyword Constraints ---
        # Script and Keyword foreign keys
        cur.execute("ALTER TABLE haskeyword ADD CONSTRAINT fk_keyword FOREIGN KEY (keywordid) REFERENCES keyword(id) ON DELETE CASCADE;")
        cur.execute("ALTER TABLE haskeyword ADD CONSTRAINT fk_script FOREIGN KEY (scriptid) REFERENCES script(id) ON DELETE CASCADE;")

        # Hasreadme Constraints ---
        cur.execute("ALTER TABLE hasreadme ADD CONSTRAINT fk_package FOREIGN KEY (packageid) REFERENCES package(id) ON DELETE CASCADE;")
        
        conn.commit()
    except Exception, err:
        debug.error(err.message)

    cur.close()
    conn.close()
