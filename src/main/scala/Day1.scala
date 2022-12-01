@main
def day1(): Unit =
  val input = Common.getInput(1)
  val calories = input.foldLeft(Vector(0)) {
    case (calories, "")          ⇒ 0 +: calories
    case (running +: tail, cals) ⇒ (running + cals.toInt) +: tail
  }
  println(calories.max)
  println(calories.sortBy(-_).take(3).sum)
