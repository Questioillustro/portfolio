# <package>
#     LessonsLearned
# <.package>
# <description>
#     Module to generate statistics over the portfolio database
# <.description>
# <keywords>
#     statistics
# <.keywords>

import util.debug as debug
import db.query as query

def generate_all_stats():
    total_lines = 0
    
    # Language stats
    languages = query.sql_query("SELECT name FROM language;", None)

    for l in languages:
        lang = l[0]
        print "--", lang, " -- "

        files_for_lang = query.sql_query("SELECT s.path||'\\'||s.name FROM script as s,language as l WHERE s.languageid = l.id AND l.name = %s;", [lang])
        print "File count: ", len(files_for_lang)

        line_count = 0    # reset line count
        for f in files_for_lang:
            lines_ = len(open(f[0], "r").readlines())
            line_count += lines_
            total_lines += lines_

        print "Total lines: ", line_count
        print "Avg. lines per file: ", line_count / len(files_for_lang)
        print

    print '++ Overall ++'
    # Package stats
    pkgs = query.sql_query("SELECT count(*) FROM package;", None)
    print 'Package count: ', pkgs[0][0]

    # Gather stats on the unsorted files
    files_unsorted = query.sql_query("SELECT file FROM unsorted;", None)
    unsorted_lines = 0
    for f in files_unsorted:
        lines_ = len(open(f[0], "r").readlines())
        unsorted_lines += lines_
        total_lines += lines_
        
    print 'Unsorted lines of code: ', unsorted_lines


    # LOC stats
    print 'Total lines of code: ', total_lines

    
    
    


