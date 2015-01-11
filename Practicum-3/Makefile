#
# Makefile for book_list Practicum
# Feel free to modify as needed
#
book_list: book_list.c
	gcc -o book_list book_list.c
clean:
	rm book_list
test:
	./book_list < sample.txt
all: clean book_list test
