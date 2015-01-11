# <package>
#     LessonsLearned
# <.package>
# <description>
#     Scans file system to find any updated files
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

initerizer.run_update(portfolio_root)
