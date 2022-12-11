case class Monkey(
    items: Seq[Long],
    f: Long ⇒ Long,
    divis: Long,
    targets: Map[Boolean, Int],
    count: Long
):
  def withItem(item: Long): Monkey = this.copy(items = items :+ item)
  def thrown: Monkey               = this.copy(items = items.tail, count = count + 1)

@main
def day11 =
  val input = parse(Common.getInputString(11))
  println(keepAway(input, 3, 20))
  println(keepAway(input, 1, 10000))

def keepAway(input: Map[Int, Monkey], divisor: Int, n: Int): Long =
  val mod = input.map(_._2.divis).product
  val monkeys = (0 until n).foldLeft(input) { case (monkeys, _) ⇒
    monkeys.keys.toSeq.sorted.foldLeft(monkeys) { case (monkeys, id) ⇒
      monkeys(id).items.foldLeft(monkeys) { case (monkeys, worry) ⇒
        val newWorry = monkeys(id).f(worry) / divisor % mod
        val target   = monkeys(id).targets(newWorry % monkeys(id).divis == 0)
        monkeys + (
          target → monkeys(target).withItem(newWorry),
          id     → monkeys(id).thrown
        )
      }
    }
  }

  monkeys.toList.map(_._2.count).sorted.reverse.take(2).product

def parse(input: String): Map[Int, Monkey] =
  val operationRe = raw"(?s).*Operation: new = old ([+*]) (\d+|old).*".r
  val targetRe    = raw"If (true|false): throw to monkey (\d+)".r
  input
    .split("\n\n")
    .toVector
    .map { s ⇒
      val id = raw"Monkey (\d+)".r.findFirstMatchIn(s).get.group(1).toInt
      val items =
        raw"Starting items: ([\d, ]+)".r
          .findFirstMatchIn(s)
          .get
          .group(1)
          .split(",")
          .toVector
          .map(i ⇒ i.trim.toLong)

      val divis = raw"Test: divisible by (\d+)".r.findFirstMatchIn(s).get.group(1).toInt

      val f: Long ⇒ Long = s match
        case operationRe("+", digits) ⇒ x ⇒ x + digits.toInt
        case operationRe("*", "old")  ⇒ x ⇒ x * x
        case operationRe("*", digits) ⇒ x ⇒ x * digits.toInt

      val targets = targetRe
        .findAllMatchIn(s)
        .toVector
        .map { m ⇒
          val truth = m.group(1) match
            case "true"  ⇒ true
            case "false" ⇒ false
          (truth, m.group(2).toInt)
        }
        .toMap

      (id, Monkey(items, f, divis, targets, 0))
    }
    .toMap
