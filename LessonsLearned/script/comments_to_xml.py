import xml.etree.ElementTree as ET
import os, time
import re
import datetime
import ll_toolkit

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

