<package>
	LessonsLearned
<.package>

********************************************************************************************************************************** 

Lessons Learned
Portfolio application
by Stephen Brewster

**********************************************************************************************************************************

rebuild_db.py --

Primary module for building a fresh database from the ground up. Drops the tables, creates them new, creates roles, sets permissions, and adds constraints to the tables.

update_db.py --

Updates the content for any files that have changed and deletes any that are missing from the file system. Will also insert new scripts that are found in the near future.

search_interface.py --

Database query module, performs simple searching and advanced sql queries on the database using a command line interface.

create_tables.py --

Creates all required tables in the database.

drop_tables.py --

Drops all tables in the database.

alter_tables.py --

Creates roles, sets permissions and constraints.

ll_db_io.py --

Contains admin level database IO sql methods.

query.py --

Contains methods for executing queries on the database, access is restricted to select statements.

ll_toolkit.py --

Assorted utility methods for managing application features.

pg_get_connection.py --

Supplies a connection and cursor object for the database, one method for an admin level connection and another for a 'SELECT' only visitor connection.

debug.py --

Implements logging logic. Outputs log messages to a file and console.