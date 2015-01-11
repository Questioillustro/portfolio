<package>
	C Project 1 - swen250
<.package>

SWEN-250 Project Activity Journal
C Health Monitoring System Project - Part 1

Name: Stephen Brewster

===========  Stage 1  ==========================================
Start
Estimated Time	1 hour
Plan:
(Order of implementation, testing approach, estimation technique)
1. Create script that compiles and runs the program, then verifies output
    1.1 Refresh knowledge of linux scripts
2. Create test data to feed stdin
    2.1 Commit
3. Implement method to read input and print the line
    3.1 Update this document and commit

Changes:
4. Creating .c files and test files for each level
    4.1 This will allow each level to persist throught the project and be
        executed using parameters passed to the run script

Complete
Actual Time	2 hours
Observations:
(Lessons learned, problems encountered, obstacles overcome, etc.)
Time ratio: 2:1
Spent a lot of time trying to get my design working properly. A unique combination of problems between
make and the c file includes caused me to chase ghosts for a while. Gained a much better understanding
of C structures.

Spent another 30 minutes adding a script to compare the output with the expected
output.

===========  Stage 2  ==========================================
Start
Estimated Time 20 minutes
Plan:
(Order of implementation, testing approach, estimation technique)
Create expected output file and test file. Use utilities already in place after
completing lvl 1 to parse the lines of input and store the data until the print
command is discovered. Print the line using proper format (Create a function
to do this)

Complete
Actual Time	1 hour
Observations:
(Lessons learned, problems encountered, obstacles overcome, etc.)
Ratio 3:1
Found that my parse_line function had to be slightly redesigned to properly work with future Levels.
Spent more time than expected designing the utility methods to properly work with all future levels.
Realized I did not perfectly understand the intended uses for the typedef structs provided, spent
a little time reviewing how they will work in level 2 as well as future levels and worked my design
around them. Overall this level went very smooth, very few compilation errors, mainly spent time
on design.

===========  Stage 3  ==========================================
Start
Estimated Time	15 minutes
Plan:
(Order of implementation, testing approach, estimation technique)
My current design should handle this level fairly easily, I should only need to add logic to the
level 2 solution that increments the buffer pointers and add a condition in the print function to
print the '<NONE>' string.

Complete
Actual Time	17 minutes
Observations:
Ratio 1.1:1
(Lessons learned, problems encountered, obstacles overcome, etc.)
Only small/expected obstacles on this level. Found the logic needed to properly print multiple stats
and the <none> string when no reading is given.

===========  Stage 4  ==========================================
Start
Estimated Time	15 mintues
Plan:
(Order of implementation, testing approach, estimation technique)
Create test with more than 10 readings of a type to exercise the circular buffer.
Plan out how to implement the circular buffer to store > 10 readings and print the readings in
chronological order. Implement and test.

Complete
Actual Time	12 minutes
Observations:
(Lessons learned, problems encountered, obstacles overcome, etc.)
Ratio .8:1
Finished a little faster than expected, found the circular buffer fairly easy to implement. Noticed
that printing all 10 values when the buffer is full seems to be impossible to accomplish reliably. 
Printing would need to include the end index, but when the buffer is not full, printing the end index
would produce garbage.

===========  Stage 5  ==========================================
Start
Estimated Time	10 minutes
Plan:
(Order of implementation, testing approach, estimation technique)
Just need to add an array of charts that index the patients id, allowing values for up to 5 patients
to be concurrently stored and printed.

Complete
Actual Time	10 minutes
Observations:
(Lessons learned, problems encountered, obstacles overcome, etc.)
Ratio 1:1
Finished on time, very simple to just add an array of charts and adjust the primary method to account
for the change.

==========i=  PROJECT PART 1 SUMMARY REFLECTION  =======================
Estimated total = 2 hours
Actual total    = 3 hours 39 minutes
Ratio           = 1.8:1
Overall the project was great experience in C. I tried to modularize the project so that the test for
each level would still be accessible by passing an argument to the bash script. It took a great deal
of extra time to learn the bash and C necessary to accomplish this, but it was a good learning
experience. I would not say the difficulty of the project itself was reasonable, I did not include all
the time I spent on the extra design in my estimates, so I spent several hours more than the AJ shows.
My estimations were fairly innacurate for the first couple stages due to problems encountered getting
C to do what I wanted it to. As I progressed through to the latter stages it became easier to accurately
estimate the time because I understood the requirements better and there was ultimately less functionality
to create.
================================================================
