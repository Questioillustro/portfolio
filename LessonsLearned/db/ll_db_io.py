# <package>
#     LessonsLearned
# <.package>
# <description>
#     Module to handle SQL transactions with the database for the LessonsLearned application
# <.description>
# <keywords>
#     psycopg2, postgresql, database, try, except, sql
# <.keywords>

import pg_get_connection
import util.debug as debug

def insert_new_script(values):
    if values["package"] == None:
        return
    
    cur, conn = pg_get_connection.create_conn("portfolio")
    new_id = None;
    
    # insert to script table
    try:
        # Execute Script insert
        cur.execute("INSERT INTO script (name, content, path, description, packageid, last_modified, created_on, languageid) VALUES(%s, %s, %s, %s, %s, %s, %s, %s) RETURNING id;",
            (values["name"], values["content"], values["path"], values["description"], values["packageId"], values["lastModified"], values["createdOn"], values["languageId"]))
        new_id = cur.fetchone()[0]
        
        conn.commit()
        
        debug.normal("Inserted new script with id: " + str(new_id))
    except Exception, err:
        debug.error("Failed to insert file " + values["path"] + "\\" + values["name"])
        debug.error(err.message)

    cur.close()
    conn.close()
    
    return new_id

def insert_package(values):
    if values["package"] == None:
        return None
    
    cur, conn = pg_get_connection.create_conn("portfolio")
    exists = 0
    
    # Check for package existence
    try:
        cur.execute("SELECT id FROM package WHERE name = %s", [values["package"]])
        exists = cur.fetchone()
    except Exception, err:
        debug.error("Failed to lookup package.")
        debug.error(err.message)

    if exists > 0:
        return exists[0]
    
    # Doesn't exist yet so insert
    try:
        cur.execute("INSERT INTO package (name) VALUES(%s) RETURNING id", [values["package"]])
        package_id = cur.fetchone()
        conn.commit()
        debug.normal("Inserted new package " + str(values["package"]))
    except Exception, err:
        debug.error("Failed to insert package.")
        debug.error(err.message)
    
    cur.close()
    conn.close()
    
    return package_id[0]

def insert_language(values):
    cur, conn = pg_get_connection.create_conn("portfolio")
    exists = 0
    
    # Check for package existence
    try:
        cur.execute("SELECT id FROM language WHERE name = %s", [values["language"]])
        exists = cur.fetchone()
    except Exception, err:
        debug.error("Failed to lookup package.")
        debug.error(err.message)

    if exists > 0:
        return exists[0]
    
    # Doesn't exist yet so insert
    try:
        cur.execute("INSERT INTO language (name) VALUES(%s) RETURNING id", [values["language"]])
        language_id = cur.fetchone()
        conn.commit()
        debug.normal("Inserted new language " + str(values["language"]))
    except Exception, err:
        debug.error("Failed to insert language.")
        debug.error(err.message)
    
    cur.close()
    conn.close()
    
    return language_id[0]
            
def insert_new_keywords(values, script_id):
    # don't insert null
    if values["keywords"] == None:
        return;

    cur, conn = pg_get_connection.create_conn("portfolio")
    keyword_ids = []
    word_clean = '';
    keywords = values["keywords"].split(",")

    # Iterate through keywords and add insert them if they don't exist
    # Retrieve the id of the keyword if it exists
    # Returns the list of keyword id(s) that goes to the script
    for word in keywords:
        word_clean = word.strip()
        exists = ''

        # First see if the keyword already exists
        try:
            cur.execute("""SELECT * FROM keyword WHERE keyword = %s;""", [word_clean])
            exists = cur.fetchone()
        except Exception, err:
            debug.error("Lookup failed: " + word_clean)
            debug.error(err.message)

        # Doesn't exist yet so insert
        if exists == None:
            try:
                cur.execute("INSERT INTO keyword (keyword) VALUES(%s) RETURNING id;", [word_clean])
                keyword_id = cur.fetchone()[0] # get the id of the new keyword
                
                conn.commit()
                
                keyword_ids.append(keyword_id)
                debug.normal("Inserted new keyword: " + word_clean + " with id: " + str(keyword_id))
            except Exception, err:
                debug.error("Failed to insert keyword: " + word_clean)
                debug.error(err.message)
        else:
            keyword_ids.append(exists[0]) # It already exists so add existing id to the list
            debug.normal("Keyword " + word_clean + " already exists, id: " + str(exists[0]))
    
    cur.close()
    conn.close()

    return keyword_ids

def insert_new_key_script_rel(script_id, keyword_id):
    cur, conn = pg_get_connection.create_conn("portfolio")

    try:
        cur.execute("INSERT INTO haskeyword (scriptid, keywordid) VALUES(%s, %s)",
                    (script_id, keyword_id))
        conn.commit()
        debug.normal("Insert successful. scriptid: " + str(script_id) + ", keywordid: " + str(keyword_id))
    except Exception, err:
        debug.error("Failed to insert relation for scriptid: " + str(script_id) + ", keywordid: " + str(keyword_id))
        debug.error(err.message)

    cur.close()
    conn.close()

def insert_readme(readme):
    cur, conn = pg_get_connection.create_conn("portfolio")

    try:
        cur.execute("INSERT INTO readme (path, content) VALUES(%s, %s) RETURNING id;",
                    (readme['path'], readme['content']))
        readme_id = cur.fetchone()
        conn.commit()
        debug.normal('Inserted readme at path ' + readme['path'])
    except Exception, err:
        debug.error('Failed to insert README' + readme['path'])
        debug.error(err.message)

    cur.close()
    conn.close()

    return readme_id

def insert_hasreadme(readmeId, packageId):
    cur, conn = pg_get_connection.create_conn("portfolio")

    try:
        cur.execute("INSERT INTO hasreadme (readmeid, packageid) VALUES(%s, %s);",
                    (readmeId, packageId))
        conn.commit()
        debug.normal('Inserted hasreadme relation, readmeId:' + str(readmeId) + ', packageId: ' + str(packageId))
    except Exception, err:
        debug.error('Failed to insert README relation, readmeId: ' + str(readmeId) + ', packageId: ' + str(packageId))
        debug.error(err.message)

    cur.close()
    conn.close()

def insert_unsorted(values):
    cur, conn = pg_get_connection.create_conn("portfolio")
    path_file = [values["path"] + "\\" + values["name"]]

    try:
        cur.execute("INSERT INTO unsorted (file) VALUES(%s)",
                    path_file)
        conn.commit()                
        debug.normal("Inserted unsorted script to " + path_file[0])
    except Exception, err:
        debug.error("Failed to insert relation for scriptid: " + str(script_id) + ", keywordid: " + str(keyword_id))
        debug.error(err.message)

    cur.close()
    conn.close()

# args is the tuple argument list
def sql_update(query, args):
    cur, conn = pg_get_connection.create_conn("portfolio")
    
    try:
        cur.execute(query, args)
        conn.commit()
    except Exception, err:
        debug.error("Query failed: " + err.message)

    cur.close()
    conn.close()

