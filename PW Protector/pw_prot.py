# <package>
#     Password Protector
# <.package>
# <description>
#     Stores username password pairs for a site in a shift cypher encryption using a passphrase
# <.description>
# <keywords>
#     shift cypher, passphrase, text file database, file
# <.keywords>

import fileinput
import re

CMDS = {".printall" : "Prints all stored passwords",
        ".create"   : "Creates a new password entry",
        ".find"     : "Searches for an entry",
        ".quit"     : "Guess what this does?!",
        ".newkey"   : "Enter new key",
        ".delete"   : "Delete an entry"}

PROMPTS = {"EnterLogin" : "Enter 'site,login,pw':",
           "Header" : "\n ********* PASSWORD LOCKER **********\n"}

def en_de_crypt_pw(pw, action):
    global KEY
    shifted = ''
    
    for i in range(len(pw)):
        ascii = ord(pw[i])
        if (action == "decrypt"):
            ascii -= ord(KEY[i%len(KEY)])
        else:
            ascii += ord(KEY[i%len(KEY)])

        if ascii > 0:
            shifted += chr(ascii)

    return shifted

def create_new():
    csv_info = raw_input(PROMPTS["EnterLogin"])
    encrypted = en_de_crypt_pw(csv_info, "")
    sorted_list = []
    
    for line in open("passwords.txt","r").readlines():
        sorted_list.append(line)

    sorted_list.append(encrypted + "\n")
    sorted_list.sort()
        
    with open("passwords.txt", "w") as pwfile:
        for i in range(len(sorted_list)):
            pwfile.write(sorted_list[i])

    print "Saved."

def delete_entry():
    lines = []
    with open("passwords.txt", "r") as f:
        lines = f.readlines()
        for index in range(len(lines)):
            print str(index+1) + ". " + en_de_crypt_pw(lines[index], "decrypt")
        
    id = input("Enter number to delete: ")
    with open("passwords.txt", "w") as f:
        for index in range(len(lines)):
            if (index+1) != id:
                f.write(lines[index])

    print "Deleting " + str(id)
    return  

def find_entry():
    search = raw_input("Search: ")
    for line in open("passwords.txt","r").readlines():
        entry_list = []
        entry_list.append(en_de_crypt_pw(line, "decrypt"))

        entry_map = {}
        for entry in entry_list:
            entry.strip("\n")
            arr = entry.split(",")

            a = re.compile(search)
            if a.match(entry):
                print entry
    
    return

def new_key():
    global KEY
    KEY = raw_input("New key: ")

def print_all():
    i = 1
    for line in open("passwords.txt","r").readlines():
        print str(i+1) + ". " + en_de_crypt_pw(line, "decrypt")
        i += 1
           
def print_commands():
    global CMDS
    for key in CMDS:
        print key + " -- " + CMDS[key]

DISP = {".printall" : print_all,
        ".create" : create_new,
        ".find" : find_entry,
        ".newkey" : new_key,
        ".delete"  : delete_entry}


KEY = raw_input("Enter Key: ")
cmd = ''
while cmd != ".quit":
    print PROMPTS["Header"]
    print_commands()
    cmd = raw_input(">> ")
    DISP[cmd]()
    raw_input("ENTER...")
