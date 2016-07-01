package mainapp

import scala.Iterator
import scala.Range
import scala.io.Source
import scala.collection.mutable.ListBuffer

case class Match()
{
  /*
   * private classes
   */
  
  //definition for the parser
  private case class DataParser(filename: String)
  {
    def getData(): List[Integer] = 
    {
      //retrieve the data and close the file
      val source = Source.fromFile(filename)
      val lines = source.getLines().toList
      source.close
      //remove empty lines and convert input to integer values
      lines.filter(_.length > 0).map(Integer.decode(_))      
    }
    def parse(): List[MatchEvent] = 
    {
      def parseData(event: Integer): MatchEvent = 
      {
        val pointsScoredMask: Int = 0x3
        val whoScoredMask: Int = 0x4
        val team2TotalMask: Int = 0x7F8
        val team1TotalMask: Int = 0x7F800
        val timeElapsedMask: Int = 0x7FF80000
        
        val pointsScored: Int = event & pointsScoredMask
        val whoScored: Int = (event & whoScoredMask) >> 2
        val team2Total: Int = (event & team2TotalMask) >> 3
        val team1Total: Int = (event & team1TotalMask) >> 11
        val timeElapsed: Int = (event & timeElapsedMask) >> 19
        MatchEvent(pointsScored, whoScored, team2Total, team1Total, timeElapsed)
      }
      DataCleaner(getData.map(parseData(_))).clean()
    }
  }
  //definition for the cleaner
  private case class DataCleaner(data: List[MatchEvent])
  {
    def clean(): List[MatchEvent] = 
    {
      val buffer = ListBuffer[MatchEvent]()

      for
      {
        i <- Range(0, data.length)
        val thisEvent = data(i)
        if(thisEvent.pointsScored != 0) //exclude events that do not offer new info
      }
      {
        if(i == data.length - 1)
        {
          buffer += thisEvent
        }
        else
        {
          val nextEvent = data(i + 1)

          //exclude events that are not in chronological order
          if(thisEvent < nextEvent)
          {
            //fix discrepancies in team 1 total
            if(thisEvent.team1Total > nextEvent.team1Total)
            {
              thisEvent.team1Total = nextEvent.team1Total
            }

            //fix discrepancies in team 2 total
            if(thisEvent.team2Total > nextEvent.team2Total)
            {
              thisEvent.team2Total = nextEvent.team2Total
            }
            
            buffer += thisEvent
          }
        }
      }
      
      buffer.toList
    }
  }
  
  /*
   * match state
   */
  
  //hold the events of the match
  private var events = List[MatchEvent]()
  
  /*
   * match behaviour - public interface
   */

  def getLastEvent() = events.lastOption
  def getAllEvents() = Iterator(events: _*)
  def getLastEvents(n: Int) = if(n > events.length) Iterator(Nil: _*) else Iterator(events.slice(events.size - n, events.size): _*)   
  def update(filename: String)
  {
    val data = DataParser(filename).parse  //parse the data
    events = events ::: data               //update the events
  }
  def display()
  {
    println("%15s %10s %12s %12s %12s\n".format("points scored","scorer","team1 Total","team2 Total","time Elapsed"))
    println("-----------------------------------------------------------------\n")
    events.foreach { println }
  }
}