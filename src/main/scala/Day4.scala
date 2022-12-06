@main
def day4(): Unit =
  val input = Common.getInput(4)
  val expr  = raw"(\d+)-(\d+),(\d+)-(\d+)".r
  val parsedInput = input.map { case expr(s1, e1, s2, e2) ⇒
    ((s1.toInt, e1.toInt), (s2.toInt, e2.toInt))
  }

  val score1 = parsedInput.count { case ((s1, e1), (s2, e2)) ⇒ contains(s1, e1, s2, e2) }
  println(score1)
  val score2 = parsedInput.count { case ((s1, e1), (s2, e2)) ⇒
    contains(s1, e1, s2, e2) || s1 <= s2 && s2 <= e1 || s2 <= s1 && s1 <= e2
  }
  println(score2)

def contains(s1: Int, e1: Int, s2: Int, e2: Int): Boolean =
  s1 >= s2 && e1 <= e2 || s2 >= s1 && e2 <= e1
