/*
<package>
	Concurrent TSA in Scala - swen342
<.package>
<description>
    Simple wrapper class for a Passenger 
<.description>
<keywords>
	wrapper, trivial
<.keywords>
*/

import collection.mutable.Map

/**
 * @author TheWizard
 */
class BagScanDone(passed: Boolean, id: Int) {
  def getResult = passed
  def getId = id
  
  def getResultMap : (Int, Boolean) = {
    return (id, passed)
  }
}