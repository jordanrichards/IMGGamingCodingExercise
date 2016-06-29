package mainapp

class MatchState(private var _pointsScored: Int)
{
  def pointsScored = _pointsScored
  def pointsScored_= (pointsScored: Int)
  {
    if(pointsScored > _pointsScored)
    {
      _pointsScored = pointsScored
    }
  }
  
}

object MatchState
{
  def apply(pointsScored: Int) = new MatchState(pointsScored)
}