/*
<package>
	Concurrent TSA in Scala - swen342
<.package>
<description>
	Simple bag scan wrapper for passengers 
<.description>
<keywords>
    wrapper, trivial
<.keywords>
*/

import collection.mutable.Map

/**
 * @author TheWizard
 */
class BodyScanDone(passed: Boolean, id: Int) {
  def getResult = passed
  def getId = id
  
  def getResultMap : (Int, Boolean) = {
    return (id, passed)
  }
}