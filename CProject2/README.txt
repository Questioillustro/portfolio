<package>
	C Project 2 - swen250
<.package>

4010-250 Project Journal
C Health Monitoring System Project - Part 2

Name: Stephen Brewster

===========  Stage 1  ==========================================
Start
Estimated Time	3 hours 30 minutes
Plan:
(Order of implementation, testing approach, estimation technique)
1. Create a bash script for generating random test data to best test each level.
2. Configure script to generate data for 1 patient check in
3. Create skeleton for reading in data until EOF within main
4. Implement addPatient
5. Implement print for patient check-in

Complete
Actual Time	3 hours 45 minutes
Observations:
(Lessons learned, problems encountered, obstacles overcome, etc.)
Ratio 1.07:1
Overall no real problems. Learned some new bash functions related to functions and 
random number generation. Finished very close to the estimated time. Most of the time
in this level was in creating the bash script to aid with testing throughout the
remaining levels. The requirements for the level were easily completed using CProject1
as a reference.

===========  Stage 2  ==========================================
Start
Estimated Time	1 hour 30 min
Plan:
(Order of implementation, testing approach, estimation technique)
Estimation based on 45 minutes to figure out how to get my test script to produce
the data correctly and 30 minutes to set up the linked list data structure and verify
1. Modify bash testing script to include cmd=9 before any health data is given for that type
    1.1. commit
2. Implement the getChart to retrieve the patient's chart
    2.1. commit
3. Implement the addHealthType method to create the node for the health type in the patients linked list
4. Test
5. Update journal and commit

Complete
Actual Time      1 hour 15 minutes
Observations:
(Lessons learned, problems encountered, obstacles overcome, etc.)
Ratio .83:1
Finished a little under my estimate. Implementing the getChart and addHealthType were simple
after completing the pointer and linkedlist activities. They were excellent preparation for
this level. Spent most of the time on the bash script to generate appropriate tests.

===========  Stage 3  ==========================================
Start
Estimated Time	5 minutes
Plan:
(Order of implementation, testing approach, estimation technique)
The manner in which I implemented the previous level will allow this level to be trivial.

Complete
Actual Time	5 minutes
Observations:
(Lessons learned, problems encountered, obstacles overcome, etc.)
Ratio 1:1
Found a minor bug in my implementation of getChart that was not exposed by 1 patient. Forgot to
iterate the pointer! Still finished in the time estimated.

===========  Stage 4  ==========================================
Start
Estimated Time	45 minutes
Plan:
(Order of implementation, testing approach, estimation technique)
5 minutes to modify test script. 10-15 minutes to implement the 'get health type for patient' method. 10-15
minutes to implement the print method. 5-10 minutes to test and tweak.

1. Modify bash script to provide test data for 1 patient and multiple health readings (<9) for a health type
2. Implement method to find the health type list for a patient
	2.1 Commit
3. Implement print method to print the data for a patient once cmd=6 is read
	3.1 Commit
4. Test 
	4.1 Ensure none is printed for non-existent data types
	4.2 Ensure data is accurate and formatting is correct
5. Update this document and commit level 4 

Complete
Actual Time	HH MM
Observations:
(Lessons learned, problems encountered, obstacles overcome, etc.)


===========  Stage 5  ==========================================
Start
Estimated Time	HH MM
Plan:
(Order of implementation, testing approach, estimation technique)

Complete
Actual Time	HH MM
Observations:
(Lessons learned, problems encountered, obstacles overcome, etc.)

===========  Stage 6  ==========================================
Start
Estimated Time	HH MM
Plan:
(Order of implementation, testing approach, estimation technique)

Complete
Actual Time	HH MM
Observations:
(Lessons learned, problems encountered, obstacles overcome, etc.)

===========  Stage 7  ==========================================
Start
Estimated Time	HH MM
Plan:
(Order of implementation, testing approach, estimation technique)

Complete
Actual Time	HH MM
Observations:
(Lessons learned, problems encountered, obstacles overcome, etc.)


===========  PROJECT SUMMARY REFLECTION  =======================
(Replace this text with a couple of paragraphs summarizing your
experience with this project. What is too easy or too hard? What
was the most challenging technical problem you faced? How
did your estimated and actual time compare? Did your estimates
improve as you advanced to later stages? Why or why not?)
================================================================
