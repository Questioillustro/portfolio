# <description>
# Pulls comment style tags out and creates xml document files in the package root directory
# <.description>

import xml.etree.ElementTree as ET
import xml.etree as etree
from xml.etree.ElementTree import ElementTree
import xml.dom.minidom as minidom
import os, time
import re
import datetime
import ll_toolkit

# Algorithm to pretty print xml from: norwied.wordpress.com/2013/08/27/307
def indent(elem, level=0):
  i = "\n" + level*"  "
  if len(elem):
    if not elem.text or not elem.text.strip():
      elem.text = i + "  "
    if not elem.tail or not elem.tail.strip():
      elem.tail = i
    for elem in elem:
      indent(elem, level+1)
    if not elem.tail or not elem.tail.strip():
      elem.tail = i
  else:
    if level and (not elem.tail or not elem.tail.strip()):
      elem.tail = i

# Extracts Lessons Learned tags from a file and returns dict      
def get_values(path, f):
    rootname = ''
    extension = ''

    # Check the file extension
    try:
        splitted = f.split(".")
        extension = splitted[1]
        rootname = splitted[0]
    except:
        pass

    if extension in ll_toolkit.get_supported_types():
        fname = os.path.join(path, f)
        with open(fname, "r") as content_f:
            content = content_f.read()              

            # Build the dictionary of values needed for the db tables
            values = {}
            values["description"] = ll_toolkit.get_description(content)
            values["keywords"] = ll_toolkit.get_keywords(content)
            values["package"] = ll_toolkit.get_package(content)
            return values
        
# Takes a CSV list of keywords and attaches them to a root xml element
def keywords_to_xml(keywords, root):
    if keywords == None:
        return None

    for keyword in keywords.split(','):
        key_xml = ET.SubElement( root, 'keyword' )
        key_xml.text = keyword.strip()

# Takes a description and attaches it to xml root
def description_to_xml(description, root):
    if description == None:
        return None

    des_xml = ET.SubElement( root, 'description')
    des_xml.text = description.strip()
        

# Scans a given directory for valid software files
# Extracts LL tags and places into xml files at the root for that package
def update_tags_to_xml(root):
    
    # Recursively walk the filesystem using the given root directory
    for directory in os.listdir( root ):
        # Naming convention, root folder name = package name
        package = ET.Element( 'package' )
        package.text = directory

        # Generate path and new xml file strings
        pkgPath = os.path.join( root, directory )
        newXML = os.path.join( pkgPath,'LL_descriptor.xml' )

        # Scan each package and build the xml file
        for path, directories, files in os.walk( pkgPath ):
            for f in files:
                values = get_values( path, f )
                f_xml = ET.SubElement( package, f )

                if values != None:
                    description_to_xml( values['description'], f_xml )
                    keywords_to_xml( values['keywords'], f_xml )

        indent(package)
        ElementTree(package).write(newXML, xml_declaration=True, encoding='utf-8', method='xml')       

        
update_tags_to_xml('B:\\Portfolio')

