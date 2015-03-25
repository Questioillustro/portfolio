# <package>
#     LessonsLearned
# <.package>
# <description>
#     Debugging module for Python, prints various debug statements
# <.description>
# <keywords>
#     debug, ouput manager
# <.keywords>

import datetime
import time

def normal(arg1):
    ts = time.time()
    st = datetime.datetime.fromtimestamp(ts).strftime('%Y-%m-%d %H:%M:%S')
    
    with open("log\log.txt", "a") as l:
        l.write(st + "\t" + arg1+"\n")

<<<<<<< HEAD
    print(st + "\t" + arg1)
=======
    print st + "\t" + arg1
>>>>>>> 614b69f8c243d467d4942b4739adb37d8def7cb8
        
def error(arg1):
    ts = time.time()
    st = datetime.datetime.fromtimestamp(ts).strftime('%Y-%m-%d %H:%M:%S')
      
    with open("log\log.txt", "a") as l:
        l.write("\n" + st + "\t ERROR " + arg1+"\n\n")

<<<<<<< HEAD
    print(st + "\t" + arg1)
=======
    print st + "\t" + arg1
>>>>>>> 614b69f8c243d467d4942b4739adb37d8def7cb8

def clear():
    with open("log\log.txt", "w") as l:
        return
