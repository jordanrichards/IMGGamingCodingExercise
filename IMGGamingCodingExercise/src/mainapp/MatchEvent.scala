package mainapp

class MatchEvent(var pointsScored:Int,var whoScored:Int,var team2Total:Int,var team1Total:Int,var timeElapsed:Int) extends Ordered[MatchEvent]
{
  def compare(that: MatchEvent) = this.timeElapsed - that.timeElapsed
  def scorer = if(whoScored == 0) "player 1" else "player 2"
  override def toString(): String = 
  {
    "%15s %10s %12d %12d %12d\n".format(pointsScored,scorer,team1Total,team2Total,timeElapsed)
  }
}

object MatchEvent
{
  def apply(pointsScored:Int,whoScored:Int,team2Total:Int,team1Total:Int,timeElapsed:Int) = new MatchEvent(pointsScored,whoScored,team2Total,team1Total,timeElapsed)
}