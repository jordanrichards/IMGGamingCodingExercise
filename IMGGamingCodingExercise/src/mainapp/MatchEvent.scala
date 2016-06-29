package mainapp

case class MatchEvent(pointsScored:Int,whoScored:Int,team2Total:Int,team1Total:Int,timeElapsed:Int)
{
  def scorer = if(whoScored == 0) "player 1" else "player 2"
  override def toString(): String = 
  {
    var txt = "===================================\n"
    txt += s"points scored so far: $pointsScored\n" 
    txt += s"scorer: $scorer\n" 
    txt += s"team 1 total: $team1Total\n" 
    txt += s"team 2 total: $team2Total\n" 
    txt += s"time elapsed: $timeElapsed\n"
    txt
  }
}
