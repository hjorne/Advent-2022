@main
def day6 =
  val input = Common.getInputString(6)
  println(distinct(input, 4))
  println(distinct(input, 14))

def distinct(input: String, n: Int): Int =
  input.zipWithIndex.sliding(n).filter(_.map(_._1).distinct.length == n).next.last._2 + 1
