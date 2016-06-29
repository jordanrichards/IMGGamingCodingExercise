package mainapp

import scala.collection.immutable.Queue
import scala.collection.mutable.ListBuffer

class MatchDataUtility
{
  private var matchEvents = List[MatchEvent]()
  
  def getLastEvent(): Option[MatchEvent] = matchEvents.lastOption
  def getLastEvents(n: Int): List[MatchEvent] = 
  {
    if(n > matchEvents.length) Nil else matchEvents.slice(matchEvents.size - n, matchEvents.size)
  }
  def getAllEvents(): List[MatchEvent] = matchEvents
  
  def convertInput(data: Int): MatchEvent = 
  {
    val pointsScoredMask: Int = 0x3
    val whoScoredMask: Int = 0x4
    val team2TotalMask: Int = 0x7F8
    val team1TotalMask: Int = 0x7F800
    val timeElapsedMask: Int = 0x7FF80000
    
    val pointsScored: Int = data & pointsScoredMask
    val whoScored: Int = (data & whoScoredMask) >> 2
    val team2Total: Int = (data & team2TotalMask) >> 3
    val team1Total: Int = (data & team1TotalMask) >> 11
    val timeElapsed: Int = (data & timeElapsedMask) >> 19
    MatchEvent(pointsScored, whoScored, team2Total, team1Total, timeElapsed)
  }
  
  def insertData(data:Array[Integer]): Boolean =
  {
    def cleanData()
    {
      val buffer = ListBuffer.empty[MatchEvent]
      for(i <- Range(0, data.length - 1))
      {
        val thisEvent = convertInput(data(i))
        val nextEvent = convertInput(data(i + 1))
        
        if(thisEvent.timeElapsed < nextEvent.timeElapsed)
        {  
          buffer += thisEvent
        }
      }

    }
    
    data.foreach(insertData(_)) 
    true
  }
  def insertData(data:Int): Boolean =
  {
    val event = convertInput(data)
    matchEvents = matchEvents ::: List(event)
    true
//    pointsScored == 2 && whoScored == 0 && team2Total == 0 && team1Total == 2 && timeElapsed == 15
  }
}