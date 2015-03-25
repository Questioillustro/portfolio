/*
<package>
	Frequency Accuracy Test
<.package>
<description>
    Implemented on Freescale microcontroller 
<.description>
<keywords>
	real-time, histogram
<.keywords>
*/

/******************************************************************************
 * Timer Output Compare Demo
 *
 * Description:
 *
 * This demo configures the timer to a rate of 1 MHz, and the Output Compare
 * Channel 1 to toggle PORT T, Bit 1 at rate of 10 Hz. 
 *
 * The toggling of the PORT T, Bit 1 output is done via the Compare Result Output
 * Action bits.  
 * 
 * The Output Compare Channel 1 Interrupt is used to refresh the Timer Compare
 * value at each interrupt
 * 
 * Author:
 *  Jon Szymaniak (08/14/2009)
 *  Tom Bullinger (09/07/2011)	Added terminal framework
 *  Stephen Brewster (09/12/2014) Changed to Histogram 
 *  Raj Muchhala
 *
 *****************************************************************************/


// system includes
#include <hidef.h>      /* common defines and macros */
#include <stdio.h>      /* Standard I/O Library */

// project includes
#include "types.h"
#include "derivative.h" /* derivative-specific definitions */

// Definitions

// Change this value to change the frequency of the output compare signal.
// The value is in Hz.
#define OC_FREQ_HZ    ((UINT16)10)

// Macro definitions for determining the TC1 value for the desired frequency
// in Hz (OC_FREQ_HZ). The formula is:
//
// TC1_VAL = ((Bus Clock Frequency / Prescaler value) / 2) / Desired Freq in Hz
//
// Where:
//        Bus Clock Frequency     = 2 MHz
//        Prescaler Value         = 2 (Effectively giving us a 1 MHz timer)
//        2 --> Since we want to toggle the output at half of the period
//        Desired Frequency in Hz = The value you put in OC_FREQ_HZ
//
#define BUS_CLK_FREQ  ((UINT32) 2000000)   
#define PRESCALE      ((UINT16)  2)         
#define TC1_VAL       ((UINT16)  (((BUS_CLK_FREQ / PRESCALE) / 2) / OC_FREQ_HZ))

// Indexing variable for the raw_data
UINT16 tcnt_count = 0;

// Bounds for microseconds in histogram
UINT16 lower_bound = 950;
UINT16 upper_bound = 1051;

// Array to hold raw timestamps
UINT16 raw_data_bag[1001];

// Histogram data
UINT16 histogram_bag[100];

// Initializes SCI0 for 8N1, 9600 baud, polled I/O
// The value for the baud selection registers is determined
// using the formula:
//
// SCI0 Baud Rate = ( 2 MHz Bus Clock ) / ( 16 * SCI0BD[12:0] )
//--------------------------------------------------------------
void InitializeSerialPort(void)
{
    // Set baud rate to ~9600 (See above formula)
    SCI0BD = 13;          
    
    // 8N1 is default, so we don't have to touch SCI0CR1.
    // Enable the transmitter and receiver.
    SCI0CR2_TE = 1;
    SCI0CR2_RE = 1;
}


// Initializes I/O and timer settings for the demo.
//--------------------------------------------------------------       
void InitializeTimer(void)
{
  // Set the timer prescaler to %2, since the bus clock is at 2 MHz,
  // and we want the timer running at 1 MHz
  TSCR2_PR0 = 1;
  TSCR2_PR1 = 0;
  TSCR2_PR2 = 0;
    
  // Set rising edge only 
  TCTL4_EDG1A = 1;
  TCTL4_EDG1B = 0;
  
  // Set up timer compare value
  //TC1 = TC1_VAL;
  
  // Clear the Output Compare Interrupt Flag (Channel 1) 
  TFLG1 = TFLG1_C1F_MASK;
  
  // Enable the output compare interrupt on Channel 1;
  TIE_C1I = 1;  
  
  //
  // Enable the timer
  // 
  TSCR1_TEN = 1;
}

// Output Compare Channel 1 Interrupt Service Routine
// Refreshes TC1 and clears the interrupt flag.
//          
// The first CODE_SEG pragma is needed to ensure that the ISR
// is placed in non-banked memory. The following CODE_SEG
// pragma returns to the default scheme. This is neccessary
// when non-ISR code follows. 
//
// The TRAP_PROC tells the compiler to implement an
// interrupt funcion. Alternitively, one could use
// the __interrupt keyword instead.
// 
// The following line must be added to the Project.prm
// file in order for this ISR to be placed in the correct
// location:
//		VECTOR ADDRESS 0xFFEC OC1_isr 
#pragma push
#pragma CODE_SEG __SHORT_SEG NON_BANKED
//--------------------------------------------------------------       
void interrupt 9 OC1_isr( void )
{
  raw_data_bag[tcnt_count] = TC1;
  tcnt_count += 1;
          
  TFLG1   =   TFLG1_C1F_MASK;  
}
#pragma pop


// This function is called by printf in order to
// output data. Our implementation will use polled
// serial I/O on SCI0 to output the character.
//
// Remember to call InitializeSerialPort() before using printf!
//
// Parameters: character to output
//--------------------------------------------------------------       
void TERMIO_PutChar(INT8 ch)
{
    // Poll for the last transmit to be complete
    do
    {
      // Nothing  
    } while (SCI0SR1_TC == 0);
    
    // write the data to the output shift register
    SCI0DRL = ch;
}


// Polls for a character on the serial port.
//
// Returns: Received character
//--------------------------------------------------------------       
UINT8 GetChar(void)
{ 
  // Poll for data
  do
  {
    // Nothing
  } while(SCI0SR1_RDRF == 0);
   
  // Fetch and return data from SCI0
  return SCI0DRL;
}


// Entry point of our application code
//--------------------------------------------------------------       
void main(void)
{
  char pointless;
  int i, temp_int;
  
  InitializeSerialPort();
  InitializeTimer();

  // Infinite loop to perform tests 
  for(;;) {
      // Reset data point counter and prompt user
      tcnt_count = 0;
      (void)printf("Hit any key to gather data...\n\r");
      pointless = GetChar();
  
      // Set bag to default
      for(i = 0; i < 1001; i++) {
          raw_data_bag[i] = 0;  
      }
      
      // Set histogram data to default
      for(i = 0; i < 100; i++) {
          histogram_bag[i] = 0; 
      }
        
      EnableInterrupts;
      
      // Wait for 1001 data points to be collected
      while(raw_data_bag[1000] == 0) {}
      
      DisableInterrupts;
      
      // Process data and store as histogram count
      for(i = 0; i < 1001; i++) {
          temp_int = raw_data_bag[i+1] - raw_data_bag[i];
          histogram_bag[temp_int - lower_bound]++;     
      }
      
      // Print histogram data
      for(i = 0; i < 100; i++) {
          if(histogram_bag[i] != 0) {
              (void)printf("Microseconds = %u, count = %u\n\r", 
                  i + lower_bound, 
                  histogram_bag[i]
               );
          }
      }
  }
}