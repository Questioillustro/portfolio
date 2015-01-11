/*
<package>
	Concurrent TSA in Scala - swen342
<.package>
<description>
    Jail actor that receives and constructs a list of passengers taken in to custody by the TSA 
<.description>
<keywords>
	actor, messages
<.keywords>
*/

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import scala.collection.mutable.ArrayBuffer
import collection.mutable.Map

/**
 * @author TheWizard
 */
class JailActor(numLines: Int) extends Actor {
  var shutDownRec = 0
  val jailed_list = ArrayBuffer[Int]()
  
  def receive = {
    // Passenger received at lockup
    case p: Passenger => {
      println("JAIL: passenger #" + p.getId + " in lockup.")
      jailed_list += p.getId
    }
    
    // Wait for a number of shutdown messages equal to the number of lines
    case r: Poison => {
      shutDownRec += 1
      if (shutDownRec == numLines) {
        println("JAIL: shutting down!")
        for (x <- 0 to jailed_list.length-1) {
          println("Passenger #" + jailed_list(x) + " sent to permanent lockup in Guantanamo.")
        }
        context.stop(self)
      }
    }
    
    case _       => println("huh?")
  }
}