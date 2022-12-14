case class Pos(x: Int, y: Int):
  def +(other: Pos): Pos = Pos(x + other.x, y + other.y)
  def -(other: Pos): Pos = Pos(x - other.x, y - other.y)
  def move: Pos          = if x.abs > 1 || y.abs > 1 then Pos(x.sign, y.sign) else Pos(0, 0)

@main
def day9 =
  val input = Common.getInput(9)
  println(simulate(input, 2))
  println(simulate(input, 10))

def simulate(input: Seq[String], n: Int): Int =
  val re     = raw"([UDLR]) (\d+)".r
  val dirmap = Map("U" → Pos(0, 1), "D" → Pos(0, -1), "L" → Pos(-1, 0), "R" → Pos(1, 0))
  val (_, seen) = input.foldLeft((Seq.fill(n)(Pos(0, 0)), Set(Pos(0, 0)))) {
    case ((pos, seen), re(dirstr, dist)) ⇒
      (0 until dist.toInt).foldLeft((pos, seen)) { case ((pos, seen), _) ⇒
        val newPos = pos.tail.foldLeft(Seq(pos.head + dirmap(dirstr))) { case (pos, tail) ⇒
          pos :+ (tail + (pos.last - tail).move)
        }
        (newPos, seen + newPos.last)
      }
  }
  seen.size
