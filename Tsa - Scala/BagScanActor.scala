/*
<package>
	Concurrent TSA in Scala - swen342
<.package>
<description>
	Bag scanning actor, receives Passenger objects, fails the scan with a random probability, passes result to the SecurityActor 
<.description>
<keywords>
    actor, messages, random number
<.keywords>
*/

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRef

/**
 * @author TheWizard
 */
class BagScanActor(securityActor: ActorRef, id: Int) extends Actor {
  def receive = {
    // Scan the passengers bag and pass the results to the security actor
    case p: Passenger => { 
      println("Bag for Passenger #" + p.getId + " enters BAG SCANNER #" + id)
      val bagScanResult = new BagScanDone(performScan(), p.getId)
      securityActor ! bagScanResult
    }
    
    // Shutdown command received, pass it on
    case r: Poison => {
      securityActor ! r
      println("BAG SCAN #" + id + " shutting down!")
      context.stop(self)
    }
    case _ => println("Bad Message.")
  }
  
  def performScan() : Boolean = {
    val r = scala.util.Random
    val rand = r.nextDouble
    
    // 20% Chance of failing the scan
    if (rand > 0.2) {
      return true
    } else {
      return false
    }
  }
}