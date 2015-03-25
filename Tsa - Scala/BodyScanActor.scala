/*
<package>
	Concurrent TSA in Scala - swen342
<.package>
<description>
	Simulates the body scan for the TSA simulation 
<.description>
<keywords>
    actor, random number, messages
<.keywords>
*/

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

/**
 * @author TheWizard
 */
class BodyScanActor(securityActor: akka.actor.ActorRef, id: Int) extends Actor {  
  def receive = {
    // Scan the passenger and pass results to the security actor
    case p: Passenger => { 
      println("Passenger #" + p.getId + " enters BODY SCANNER #" + id)
      val bodyScanResult = new BodyScanDone(performScan(), p.getId)
      securityActor ! bodyScanResult
    }
    
    // Shutdown object, pass it on
    case r: Poison => {
      securityActor ! r
      println("BODY SCANNER #" + id + " shutting down!")
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