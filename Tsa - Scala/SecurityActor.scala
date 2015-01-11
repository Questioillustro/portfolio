/*
<package>
	Concurrent TSA in Scala - swen342
<.package>
<description>
    Receives messages from the bag and body scanners and sends passengers to the jail actor 
<.description>
<keywords>
    concurrency, actor, messages
<.keywords>
*/

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import collection.mutable.Map
import java.util.NoSuchElementException

/**
 * @author TheWizard
 */
class SecurityActor(jailActor: akka.actor.ActorRef, id: Int) extends Actor {
  var shutDownsRec = 0
  val bag_map = Map[Int, Boolean]()
  val body_map = Map[Int, Boolean]()
  
  def receive = {
    // Body scan results come in
    case body: BodyScanDone => {
      println("SECURITY LINE #" + id + " received BODY SCANNER results, passenger #" + body.getId)
      body_map += body.getResultMap
      checkScanDone(body.getId)
    }
    
    // Bag scan results come in
    case bag: BagScanDone => {
      println("SECURITY LINE #" + id + " received BAG SCANNER results, passenger #" + bag.getId)
      bag_map += bag.getResultMap
      checkScanDone(bag.getId)
    }
    
    // Shutdown object
    case r: Poison => {
      shutDownsRec += 1
      if (shutDownsRec == 2) {
        println("SECURITY LINE #" + id + " shutting down.")
        jailActor ! r
        context.stop(self)
      }
    }
    
    case _ => println("Bad Message.")
  } 
  
  /**
   * Uses Bag and Body Map globals to determine if both scans are complete
   */
  def checkScanDone(p_id: Int) {
    try {
      // Both bag and body scan PASSED
      if (bag_map.apply(p_id) && body_map.apply(p_id)) {
        println("SECURITY LINE #" + id + " all scaning PASSED, passenger #" + p_id)
      
      // Or something FAILED!
      } else if (!body_map.apply(p_id) || !bag_map.apply(p_id)){
        jailActor ! new Passenger(p_id)
        
        // Bag Scan failed
        if (!bag_map.apply(p_id) && body_map.apply(p_id)) {
          println("SECURITY LINE #" + id + " bag scan FAILED, passenger #" + p_id + " bag in quarantine.")
          
        // Body Scan failed
        } else if (!body_map.apply(p_id) && bag_map.apply(p_id)) {
          println("SECURITY LINE #" + id + " body scan FAILED, passenger #" + p_id + " target has been tazed and restrained.")
        
        // Both failed!
        } else {
          println("SECURITY LINE #" + id + " both scans FAILED, passenger #" + p_id + " shoot on site!")
        }
      }
    // Still waiting on a scan result
    } catch {
      case n: NoSuchElementException => {
        // Nothing to do  
      }
    }
  }
}