package testapp

import org.junit.Assert._
import junit.framework.TestCase
import mainapp.MatchDataUtility
import scala.io.Source

class IMGGamingCodingTester extends TestCase
{
  var matchUtility: MatchDataUtility = _
  var dataset1: Array[Integer] = _ 
  var dataset2: Array[Integer] = _ 

  override def setUp()
  {
    def getDataFromFile(filename: String) = 
    {
      val filename = "input/sample2.txt"
      val source = Source.fromFile(filename)
      val lines = source.getLines().toArray
      source.close   
      lines.filter(_.length > 0).map(Integer.decode(_))      
    }
    
    matchUtility = new MatchDataUtility
    dataset1 = getDataFromFile("input/sample1.txt")
    dataset2 = getDataFromFile("input/sample2.txt")
  }

  def testUtilityCreatedOK()
  {
    assertNotNull("the tool must have a valid reference", matchUtility)
  } 
  
  def testInsertEventSuccess()
  {
    val event = 0x781002
    assertTrue("this must be a valid message", matchUtility.insertData(event))
  }
  
  def testInsertEventFailure()
  {
    val event = 0x781001
    assertFalse("this must be a non valid message", matchUtility.insertData(event))
  }
  
  def testInsertConsistentEvents()
  {
    matchUtility.insertData(dataset2)
    matchUtility.getAllEvents().foreach(println)
    assertTrue("this must be a valid dataset", true)
  }
}