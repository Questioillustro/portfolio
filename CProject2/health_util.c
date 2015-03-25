/*
<package>
	C Project 2 - swen250
<.package>
<description>
    Utility methods for C Project 2
<.description>
<keywords>
    linked list, pointers, struct
<.keywords>
*/

#include <stdio.h>
#include <stdlib.h>
#include "health.h"


/*
* health_util.c - Helper functions for the Health Monitoring System
*
* Add any optional helper function declarations here, define 
* the functions after the required functions below.
*
* static void myOptionalFunc();   // EXAMPLE 
*
*/
char parse_line(struct Element *element, int *id, int *cmd);

/*
* addPatient: check-in a new patient
*   (1) allocate a new Chart for the patient
*   (2) initialize the chart with the passed patientID
*   (3) new patients are inserted at the start of the patient list
*
* (note that the variable patientList is globally accessible)
*/
void addPatient( int patientID ) {
    // init new chart
    Chartptr new_chart = (Chartptr)malloc( sizeof( Chartptr ) ) ;
    new_chart->id = patientID ;
    new_chart->buffer = NULL ;
    new_chart->next = NULL ;

    // if first chart -> assign to head
    if(patientList == NULL) {
        patientList = new_chart ;
    // else append new chart to the end of the patient list
    } else {
        Chartptr tail = patientList ;
        while(tail->next != NULL) {
            tail = tail->next ;
        }
        tail->next = new_chart ;
    }
}

/*
* addHealthType: add a new health type buffer for a given patient
*	(1) allocate a new CircularBuffer
*	(2) initialize the buffer
* 	(3) link it to the existing patient's Chart
*/
void addHealthType( int patientID, int newType ){
    // find the patient's chart
    Chartptr patient_chart = getChart( patientID );

    // init buffer
    CBuffptr buffer = (CBuffptr)malloc( sizeof( CBuffptr ) ) ;
    buffer->type  = newType ;
    buffer->start = 0 ;
    buffer->end   = 0 ;
    buffer->next  = NULL ;

    // append to the head of the chart when first type added
    if(patient_chart->buffer == NULL) {
        patient_chart->buffer = buffer ;
    // find the tail and append otherwise
    } else {
        CBuffptr tail = patient_chart->buffer ;
        while(tail->next != NULL) {
            tail = tail->next ;
        }
        patient_chart->buffer->next = buffer ;
    }
}
  
/*
*  getChart: given a patientID, return a pointer to their Chart
*/
Chartptr getChart( int patientID ){
    Chartptr foundChart = NULL;
    Chartptr iter = patientList ;
    while(iter != NULL) {
        if(iter->id == patientID) {
            foundChart = iter ;
            break ;
        }
        iter = iter->next ;
    }
    return foundChart;
}

/* 
*  getHealthType: given a patientID & healthType, return a pointer 
*  to the CircularBuffer for that type. If the health type does NOT exist 
*  for that patient, return NULL
*/
CBuffptr getHealthType( int patientID, int healthType ){
  CBuffptr foundType = NULL;
  
  /* YOUR CODE HERE */
  
  return foundType;
  }
 
/*
*  addHealthReading: given a pointer to CircularBuffer, add the passed
*  timestamp and health data type reading to the buffer
*/
void addHealthReading( CBuffptr buffer, char* timestamp, int reading ){

  /* YOUR CODE HERE */
}
  
/*
*  removePatient: check-out an existing patient
*	  (1) delete the patient's Chart & accompanying 
*         health data readings.
*     (2) update the list of current patients
*/
void removePatient( int patientID ){
    //printf("Removing patient ID: %d\n", patientID);
}

/*
* Optional helper functions defined starting here:
*
* static void myOptionalFunc(){ }  // EXAMPLE
*
*/
char parse_line(struct Element *element, int *id, int *cmd) {
    char ch;
    // pull PatientID
    scanf("%d", id);
    getchar();
    getchar();
    // pull TIME
    scanf("%8c", element->timestamp);
    element->timestamp[8] = '\0';
    getchar();
    getchar();
    // pull CMD
    scanf("%d", cmd);
    getchar();
    getchar();
    // pull VALUE
    scanf("%d", &element->value);
    ch = getchar();
    // ch notifies main() of EOF
    return ch ;
}
