package testapp

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

import junit.framework.TestCase
import mainapp.Match

class IMGGamingCodingTester extends TestCase
{
  var matchOption: Option[Match] = None
  val file1 = "input/sample1.txt"
  val file2 = "input/sample2.txt"

  override def setUp()
  {
    matchOption = Some(Match())
  }

  def testMatchCreatedOK()
  {
    assertFalse("the tool must have a valid reference", matchOption.isEmpty)
  } 
   
  def testInsertEventsFirstSetOK()
  {
    val matchUnit = matchOption.get
    matchUnit.update(file1)

    val events = matchUnit.getAllEvents.toList
    assertTrue("valid events must be = 28", events.size == 28)
    println("*** set 1 ***\n")
    matchUnit.display
  }
  
  def testInsertEventsSecondSetOK()
  {
    val matchUnit = matchOption.get
    matchUnit.update(file2)

    val events = matchUnit.getAllEvents.toList       
    assertTrue("valid events must be = 24", events.size == 24)
    println("*** set 2 ***\n")
    matchUnit.display
  }  
  
  def testGetLastEventOK()
  {
    val matchUnit = matchOption.get
    matchUnit.update(file1)

    val event = matchUnit.getLastEvent().get       
    assertTrue("values must be 2, 0, 27, 29, 598", event.pointsScored == 2 && event.whoScored == 0 && event.team1Total == 27 && event.team2Total == 29 && event.timeElapsed == 598)
  }

  def testGetLastEventsSizeOK()
  {
    val matchUnit = matchOption.get
    matchUnit.update(file1)

    val event = matchUnit.getLastEvents(3).toList
    assertTrue("elements must be 3", event.size == 3)
  }
  
  def testGetLastEventsFirstOK()
  {
    val matchUnit = matchOption.get
    matchUnit.update(file1)

    val events = matchUnit.getLastEvents(3).toList
    val event = events(0)
    assertTrue("values must be 1, 1, 23, 29, 559", event.pointsScored == 1 && event.whoScored == 1 && event.team1Total == 23 && event.team2Total == 29 && event.timeElapsed == 559)
  }  

  def testGetLastEventsLastOK()
  {
    val matchUnit = matchOption.get
    matchUnit.update(file1)

    val events = matchUnit.getLastEvents(3).toList
    val event = events.last
    assertTrue("values must be 2, 0, 27, 29, 598", event.pointsScored == 2 && event.whoScored == 0 && event.team1Total == 27 && event.team2Total == 29 && event.timeElapsed == 598)
  }
}

