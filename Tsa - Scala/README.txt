
Concurrent Screener in Scala

Main module:
Tsa.scala

Bugs:
No known bugs to speak of

Message passing scheme:
Tsa (Passenger)-> DocCheckActor (Passenger)-> 
LineQueueActor (Passenger)-> BagScanActor && BodyScanActor (Bag(Body)ScanDone)-> 
SecurityActor (Passenger)-> JailActor

Configurable constants:
*Located in Tsa.scala
numLines - The number of security lines to run
numPassengers - The number of passengers to generate