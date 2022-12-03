@main
def day3(): Unit =
  val input = Common.getInput(3)
  val score1 = input.map { rucksack ⇒
    val (left, right) = rucksack.splitAt(rucksack.length / 2)
    priority(left.intersect(right).head)
  }.sum
  val score2 = input
    .grouped(3)
    .map(group ⇒ priority(group.tail.fold(group.head)(_ intersect _).head))
    .sum
  println(score1)
  println(score2)

def priority(item: Char): Int = 1 + (if item.isLower then item - 'a' else item - 'A' + 26)
