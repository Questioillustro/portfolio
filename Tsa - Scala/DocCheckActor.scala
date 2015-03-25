/*
<package>
	Concurrent TSA in Scala - swen342
<.package>
<description>
    Main driver class for the TSA security simulation with akka actors 
<.description>
<keywords>
	actor, messages, random number
<.keywords>
*/

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRef
import scala.collection.mutable.ArrayBuffer

/**
 * @author TheWizard
 */
class DocCheckActor(lines: ArrayBuffer[ActorRef]) extends Actor {
  def receive = {
    // Doc Check receives a passenger and passes it to a security line
    case p: Passenger => {
      println("Passenger #" + p.getId + " arrives at DOC CHECK")
      // Peform doc check and pass to security line if cleared
      if (performCheck()) {
        println("DOCUMENT CHECK passenger #" + p.getId + " CLEARED")
        lines(p.getId%(lines.length)) ! p  
      } else {
        println("DOCUMENT CHECK passenger #" + p.getId + " FAILED, take a hike!")
      }
    }
    
    // Shutdown object received, pass it on
    case r: Poison => {
      for (x <- 0 to lines.length-1) {
        lines(x) ! r
      }
      println("DOCUMENT CHECK shutting down!")
      context.stop(self)
    }
    case _ => println("Bad Message.")
  }
    
  def performCheck() : Boolean = {
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

