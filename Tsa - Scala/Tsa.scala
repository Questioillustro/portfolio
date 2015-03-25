/*
<package>
	Concurrent TSA in Scala - swen342
<.package>
<description>
    Main driver class for the TSA security simulation with akka actors 
<.description>
<keywords>
    concurrency, actors, driver, messages
<.keywords>
*/

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import scala.collection.mutable.ArrayBuffer

/**
 * @author TheWizard
 */
object Tsa {
  def main(args: Array[String]) {
    val numLines: Int = 2;
    val numPassengers: Int = 10;

    // Root of the actor system
    val system = ActorSystem("ScreenerSystem")

    // Array of Line Queue actors
    var lineActors: ArrayBuffer[akka.actor.ActorRef] = ArrayBuffer()
    
    val jailActor = system.actorOf(Props(new JailActor(numLines)), name = "jailactor")
    
    // Generate 2 security lines, each with a bag and body scanner and security actor end point
    for (x <- 1 to numLines) {
      // Security
      val securityActor = system.actorOf(Props(new SecurityActor(jailActor, x)), name = "securityactor"+x)
      
      // Scanners
      val bagActor = system.actorOf(Props(new BagScanActor(securityActor, x)), name = "bagscanactor"+x)
      val bodyActor = system.actorOf(Props(new BodyScanActor(securityActor, x)), name = "bodyscanactor"+x)
      
      // Line queue actor
      val lineActor = system.actorOf(Props(new LineQueueActor(bagActor, bodyActor, x)), name = "lineactor"+x)
      lineActors.append(lineActor)
    }
    
    // Create the doc check actor with references to the line queues
    val docCheckActor = system.actorOf(Props(new DocCheckActor(lineActors)), name = "docheckactor")
    
    // Generate 10 passengers and pass them through the system
    for (x <- 1 to numPassengers) {
      println("NEW PASSENGER #" + x + " enters the system")
      val passenger = new Passenger(x)
      docCheckActor ! passenger  
    }
    
    // Send shutdown message through system
    docCheckActor ! new Poison()
  }
}