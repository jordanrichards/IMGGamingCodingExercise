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
   
  def testInsertEventsFirstOK()
  {
    val matchUnit = matchOption.get
    matchUnit.update(file1)

    val events = matchUnit.getAllEvents
    assertTrue("valid events must be = 27", events.size == 27)
//    events.foreach { println }
  }
  
  def testInsertEventsSecondOK()
  {
    val matchUnit = matchOption.get
    matchUnit.update(file2)

    val events = matchUnit.getAllEvents       
    assertTrue("valid events must be = 25", events.size == 25)
    events.foreach { println }
  }  
}

/*  def testInsertEventSuccess()
  {
    val event = 0x781002
    assertTrue("this must be a valid message", matchUnit.insertData(event))
  }
  
  def testInsertEventFailure()
  {
    val event = 0x781001
    assertFalse("this must be a non valid message", matchUnit.insertData(event))
  }
*/

