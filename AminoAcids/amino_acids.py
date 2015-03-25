# <package>
#     Amino Acids
# <.package>
# <description>
#     Practice with python dictionary
# <.description>
# <keywords>
#     dictionary
# <.keywords>

import sys

START_CODON = "aug"
STOP_CODONS = ["uaa","uag","uga"]
RNA_TO_AAs = {"aug":"Met","uuu":"Phe", "uuc":"Phe", "uua":"Leu", "uug":"Leu", "cuc":"Leu","cug":"Leu","auu":"Ile", "auc":"Ile", "aua":"Ile", "guu":"Val", "guc":"Val", "gua":"Val", "gug":"Val", "ucu":"Ser", "ucc":"Ser", "uca":"Ser", "ucg":"Ser", "ccu":"Pro", "ccc":"Pro", "cca":"Pro", "ccg":"Pro", "acu":"Thr", "acc":"Thr", "aca":"Thr", "acg":"Thr", "gcu":"Ala", "gcc":"Ala", "gca":"Ala", "gcg":"Ala", "uau":"Tyr", "uac":"Tyr", "cau":"His", "cac":"His", "caa":"Gln", "cag":"Gln", "aau":"Asn", "aac":"Asn", "aaa":"Lys", "aag":"Lys", "gau":"Asp", "gac":"Asp", "gaa":"Glu", "gag":"Glu", "ugu":"Cys", "ugc":"Cys", "ugg":"Trp", "cgu":"Arg", "cgc":"Arg", "cga":"Arg", "cgg":"Arg", "agu":"Ser", "agc":"Ser", "aga":"Arg", "agg":"Arg", "ggu":"Gly", "ggc":"Gly", "gga":"Gly", "ggg":"Gly" }


stdin_lines = sys.stdin.readlines()

for line in stdin_lines:
	line = line.replace("\n", "") #Remove line break to clean up output
	print('Test line:', line) 
	codons = line.split()
	translation = '' #Reset translation string for each newline
	chainInProgress = False

	#Iterate codons for the line
	for codon in codons:
		codon = codon.lower() #Convert codons to lowercase to match dictionary 
		
		if codon in START_CODON: #If it is a start codon reset the translation string
			translation = RNA_TO_AAs[codon]
			chainInProgress = True

		elif codon in STOP_CODONS: #If it is a stop codon print the compiled translation
			print(translation)
			chainInProgress = False

		elif chainInProgress and codon in RNA_TO_AAs: #If AA chain is stopped and not restarted, ignore
			translation += RNA_TO_AAs[codon]

		translation += ' ' #Append space between amino acids
	
	if chainInProgress:
		print(translation) #Default print if there is no stop codon for the line	
