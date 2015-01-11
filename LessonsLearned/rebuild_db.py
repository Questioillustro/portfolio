# <package>
#     LessonsLearned
# <.package>
# <description>
#     Main module for the LessonsLearned application
# <.description>
# <keywords>
#     driver
# <.keywords>

import util.initialize_new_database as initerizer
import util.debug as debug

db_name = "portfolio"
portfolio_root = "B:\Portfolio"

# Clear log file
debug.clear()

if initerizer.make_new(db_name):
    initerizer.scan_file_system(portfolio_root);
