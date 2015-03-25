# <package>
#     LessonsLearned
# <.package>
# <description>
#     Set of database initialization tools for the LessonsLearned application
# <.description>
# <keywords>
#     search folder, search file system, tuples
# <.keywords>

import db.drop_tables as drop_tables
import db.ll_db_io as ll_db_io
import db.query as query
import db.create_tables as create_tables
import db.alter_tables as alter_tables
import datetime

import ll_toolkit
import debug

import os, time
import re

# Rebuild the database from scratch
def make_new(dbName):
    try:
        drop_tables.drop(dbName)
        create_tables.create(dbName)
        alter_tables.create_roles()
        alter_tables.set_permissions()
        alter_tables.set_constraints()
    except Exception, err:
        debug.error("Failed to rebuild database: " + err.message)
        return False

    return True

# Scans a given directory for valid software files
# Saves valid files into the portfolio database
def scan_file_system(root):
    
    # Recursively walk the filesystem using the given root directory
    for path, directories, files in os.walk(root):
        for f in files:
            rootname = ''
            extension = ''
            
            # Check the file extension
            try:
                splitted = f.split(".")
                extension = splitted[1]
                rootname = splitted[0]
            except:
                debug.normal("Invalid file, skipping: " + f)
                
            if extension in ll_toolkit.get_supported_types():
                fname = os.path.join(path, f)
                debug.normal("Processing file " + fname)

                # Read in the content
                # Beware! Changing the second arg of open() can delete your portfolio!
                with open(fname, "r") as content_f:
                    content = content_f.read()              

                # Build the dictionary of values needed for the db tables
                values = {}
                values["description"] = ll_toolkit.get_description(content)
                values["keywords"] = ll_toolkit.get_keywords(content)
                values["package"] = ll_toolkit.get_package(content)
                values["name"] = f
                values["content"] = content
                values["path"] = path
                values["lastModified"] = time.ctime(os.path.getmtime(fname))
                values["createdOn"] = time.ctime(os.path.getctime(fname))
                values["language"] = ll_toolkit.get_language(extension)

                # if the 1 required tag is present, save the script and it's related data
                # else save the full path and filename to the unsorted table
                if values["package"] != None:
                    # Insert package or get existing id
                    values["packageId"] = ll_db_io.insert_package(values)

                    # Insert language or get existing id
                    values["languageId"] = ll_db_io.insert_language(values)

                    # Insert script
                    script_id = ll_db_io.insert_new_script(values)

                    # Insert keywords
                    key_ids = ll_db_io.insert_new_keywords(values, script_id)

                    # Insert keyword script relation
                    if key_ids != None:
                        for key_id in key_ids:
                            ll_db_io.insert_new_key_script_rel(script_id, key_id)
                else:
                    ll_db_io.insert_unsorted(values)

            # if this is a readme then insert
            if rootname.lower() == 'readme':
                fullname = os.path.join(path, f)
                
                with open(fullname, 'r') as readme_f:
                    readme_content = readme_f.read()

                readme = {
                    'content' : readme_content,
                    'path' : path,
                    'package' : ll_toolkit.get_package(readme_content)
                }

                # Insert/Get package id
                package_id = ll_db_io.insert_package(readme)
                # Insert and get readme id
                readme_id = ll_db_io.insert_readme(readme)
                # Insert relation
                ll_db_io.insert_hasreadme(readme_id, package_id)
     
def run_update(root):
    # Recursively walk the filesystem using the given root directory
    for path, directories, files in os.walk(root):
        for f in files:
            fullname = os.path.join(path, f)

            existing_rec = query.sql_query("SELECT last_modified FROM script WHERE path||'\\'||name = %s", [fullname])
            if len(existing_rec) > 0:
                # Existing last_modified in db
                existing_last_mod = existing_rec[0][0]
                d1 = datetime.datetime.strptime(str(existing_last_mod), "%Y-%m-%d %H:%M:%S")

                # Current last_modified on file
                curr_last_mod = time.ctime(os.path.getmtime(fullname))
                d2 = datetime.datetime.strptime(curr_last_mod, "%a %b %d %H:%M:%S %Y")
                
                # Update content if changed
                if d2 > d1:
                    with open(fullname, 'r') as the_file:
                        content = the_file.read()
                        
                    ll_db_io.sql_update("UPDATE script SET last_modified = %s,content = %s WHERE path||'\\'||name = %s", (curr_last_mod, content, fullname))
                    debug.normal(fullname + " updated.")

    # Scan files and make sure all files still exist in file system
    existing_files = query.sql_query("SELECT path||'\\'||name FROM script", None)
    for f in existing_files:
        fullname = f[0]
        try:
            open(fullname, 'r')
        except Exception, err:
            debug.normal('Deleting missing file' + fullname)
            ll_db_io.sql_update("DELETE FROM script WHERE path||'\\'||name = %s", [fullname])
        
        
            

    
