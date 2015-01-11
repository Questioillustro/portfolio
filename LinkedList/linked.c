/*
<package>
	Linked List in C
<.package>
<description>
    Creates a Linked List data structure and tests it
<.description>
<keywords>
    linked list
<.keywords>
*/

#include <stdio.h>
#include <stdlib.h>

struct node {
  int data;
  struct node* next;
};

static int length();
static void push(int data);
static int pop();
static void appendNode(int data);
static struct node *copyList();
static void printList();


struct node *head;		// pointer to head of linked list
						// head is visible to all functions in this file

/************************************************************
 length - return length of a list
 ************************************************************/
int length() {
    int count = 0 ;
    struct node *iter ;
    iter = head ;

    while(iter != NULL) {
        count++ ;
        iter = iter->next ;
    }

    free(iter) ;
    return count ;
}


/************************************************************
 push - add new node at beginning of list
 ************************************************************/
void push(int data) {
    struct node *oldhead = head ;

    struct node *newhead = (struct node*)malloc( sizeof( struct node) ) ;
    newhead->data = data ;
    newhead->next = oldhead ;

    head = newhead ;
}

/************************************************************
 pop - delete node at beginning of non-empty list and return its data
 ************************************************************/
int pop() {
    if(length() > 0) {
        int val = head->data ;
        struct node *popped = head ;
        free(head) ;
        head = popped->next ;
        //free(popped) ;
        return val;
    } else {
        printf("No value to pop.\n") ;
        return -1 ;
    }
}

/************************************************************
 appendNode - add new node at end of list
 ************************************************************/
void appendNode(int data) {
    // when the list is empty use the push method to avoid complicated logic
    if(length() == 0) {
        push(data) ;
    } else {
        struct node *new_node = (struct node*)malloc( sizeof( struct node ) ) ;
        new_node->data = data ;
        new_node->next = NULL ;

        struct node *tail = head ;
        while(tail->next != NULL) {
            tail = tail->next ;
        }

        tail->next = new_node ;
    }
}

/************************************************************
 copyList - return new copy of list
 ************************************************************/
struct node* copyList() {
    struct node *copy = NULL ;
    struct node *iter = head ;
    struct node *prev = NULL ;

    while(iter != NULL) {
        struct node *c_node = (struct node*)malloc( sizeof( struct node ) ) ;
        c_node->data = iter->data ;
        c_node->next = NULL ;

        if(copy == NULL) {
            copy = c_node ;
            prev = c_node ;
        } else {
            prev->next = c_node ;
            prev = c_node ;
        }
        iter = iter->next ;
    }

    return copy ;
}


/************************************************************
 printList - print linked list as "List: < 2, 5, 6 >" (example)
 ************************************************************/
void printList(struct node *list) {
    struct node* current ;

    printf("List: < ");
    current = list ;
    while(current != NULL) {
        printf("%d", current->data);
        current = current->next ;
        if(current != NULL) {
            printf(", ");
        }
    }
    printf(" >\n");
    free(current) ;
}

void main() {
    /* Initialize */
    head = NULL ;

    printList(head) ;
    printf("Length of list = %d\n", length(head)) ;

    // test push
    printf("Push(1), Push(2), Push(3)\n") ;
    push( 1 ) ;
    push( 2 ) ;
    push( 3 ) ;
    printList(head) ;
    printf("Length of list = %d\n", length()) ;

    // test append
    printf("\nAppend(2), Append(3)\n") ;
    appendNode( 2 ) ;
    appendNode( 3 ) ;
    printList(head) ;
    printf("Length of list = %d\n", length()) ;

    // test pop
    int pop1 = pop() ;
    printf("\nPopped %d\n", pop1) ;
    printList(head) ;

    int pop2 = pop() ;
    printf("Popped %d\n", pop2) ;
    printList(head) ;
    printf("Length of list = %d\n", length()) ;

    int pop3 = pop() ;
    printf("Popped %d\n", pop3) ;
    printList(head) ;

    int pop4 = pop() ;
    printf("Popped %d\n", pop4) ;
    printList(head) ;

    int pop5 = pop() ;
    printf("Popped %d\n", pop5) ;
    printList(head) ;

    // test pop boundary
    int pop6 = pop() ;
    printf("Popped %d\n", pop6) ;
    printList(head) ;

    // test appendNode boundary
    appendNode( 4 ) ;
    appendNode( 5 ) ;
    appendNode( 6 ) ;

    // test copy list
    struct node *copy = copyList() ;
    printf("\nList copied\n") ;
    printf("Original:\n") ;
    printList(head) ;
    printf("Copy:\n") ;
    printList(copy) ;

    // ensure this is deep copy
    printf("\npop(), pop(), Append(7), Append(8) to original\n") ;
    pop();
    pop();
    appendNode( 7 ) ;
    appendNode( 8 ) ;
    printf("Original:\n") ;
    printList(head) ;
    printf("Copy:\n") ;
    printList(copy) ;
}
