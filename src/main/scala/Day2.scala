@main
def day2(): Unit =
  val expr   = raw"(\w) (\w)".r
  val input  = Common.getInput(2).map { case expr(x, y) ⇒ (x.head.toInt - 65, y.head.toInt - 88) }
  val score1 = input.map(play).sum
  val score2 = input.map { case (opponent, game) ⇒
    play(opponent, Math.floorMod(opponent - 1 + game, 3))
  }.sum
  println(score1)
  println(score2)

def play(opponent: Int, self: Int): Int =
  if opponent == self then 3 + self + 1
  else if self == 2 && opponent == 0 then self + 1
  else if self > opponent || self == 0 && opponent == 2 then 6 + self + 1
  else self + 1
