/*
<package>
	Concurrent TSA in Scala - swen342
<.package>
<description>
    Gate-keeper class for messages entering each security line 
<.description>
<keywords>
	actor, queue, messages
<.keywords>
*/

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

/**
 * @author TheWizard
 */
class LineQueueActor(bagScanner: akka.actor.ActorRef, bodyScanner: akka.actor.ActorRef, id: Int) extends Actor {
  def receive = {
    case p: Passenger => {
      println("Passenger #" + p.getId + " enters SECURITY LINE #" + id)
      bagScanner ! p
      bodyScanner ! p
    }
    case r: Poison => {
      bagScanner ! r
      bodyScanner ! r
      println("LINE QUEUE #" + id + " shutting down!")
      context.stop(self)
    }
    case _ => println("Bad Message.")
  }
}