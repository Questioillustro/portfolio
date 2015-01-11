import MySQLdb as mysql
cnx = mysql.connector.connect(user='stephenportfolio', password='PhoeniX673!',
                                  host='68.178.143.79', database='stephenportfolio')

cnx.close()
