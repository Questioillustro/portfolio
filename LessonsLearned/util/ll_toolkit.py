# <package>
#     LessonsLearned
# <.package>
# <description>
#     Set of database initialization tools for the LessonsLearned application
# <.description>
# <keywords>
#     regex, open file, read file
# <.keywords>

import re
import debug

supported_types = ["py", "c", "rb", "java", "cpp", "scala"]
ext_language = {
    "py" : "python",
    "cpp" : "c++",
    "c" : "c",
    "rb" : "ruby",
    "java" : "java",
    "scala" : "scala"
}

# Add an arbitrary string to prevent the string from being matched
# when this file is processed by LL
desc_tag_s = "<description@>"
desc_tag_e = "<.description@>"
desc_tag_s = re.sub("@", "", desc_tag_s)
desc_tag_e = re.sub("@", "", desc_tag_e)

keyword_tag_s = "<keywords@>"
keyword_tag_e = "<.keywords@>"
keyword_tag_s = re.sub("@", "", keyword_tag_s)
keyword_tag_e = re.sub("@", "", keyword_tag_e)

package_tag_s = "<package@>"
package_tag_e = "<.package@>"
package_tag_s = re.sub("@", "", package_tag_s)
package_tag_e = re.sub("@", "", package_tag_e)

def get_description(content):
    description_patt = re.compile(desc_tag_s+".*"+desc_tag_e, re.DOTALL)
    result_string = description_patt.search(content)
    the_description = None
    
    if result_string != None:
        the_description = result_string.group(0)
        the_description = re.sub(desc_tag_s + "|" + desc_tag_e, "", the_description)
        the_description = re.sub("#", "", the_description).strip()

    return the_description

def get_keywords(content):
    keyword_patt = re.compile(keyword_tag_s+".*"+keyword_tag_e, re.DOTALL)
    result_string = keyword_patt.search(content)
    keywords = None
    
    if result_string != None:
        keywords = result_string.group(0)
        keywords = re.sub(keyword_tag_s + "|" + keyword_tag_e, "", keywords)
        keywords = re.sub("#", "", keywords).strip()

    return keywords

def get_package(content):
    package_patt = re.compile(package_tag_s+".*"+package_tag_e, re.DOTALL)
    result_string = package_patt.search(content)
    package = None
    
    if result_string != None:
        package = result_string.group(0)
        package = re.sub(package_tag_s + "|" + package_tag_e, "", package)
        package = re.sub("#", "", package).strip()

    return package
    
def get_supported_types():
    global supported_types
    return supported_types

def get_language(extension):
    global ext_language
    return ext_language[extension]
    
