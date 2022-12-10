//case class Instr(count: Int, target: Int, dest: Int)
//
//@main
//def day5(): Unit =
//  val input      = Common.getInput(5)
//  val stackInput = input.takeWhile(_ != "")
//  val instrInput = input.dropWhile(_ != "").tail
//  val stackRe    = raw"(\w)".r
//  val stack = stackInput
//    .flatMap { s ⇒
//      stackRe
//        .findAllMatchIn(s)
//        .map(m ⇒ (m.toString, (m.start - 1) / 4 + 1))
//        .toVector
//    }
//    .groupBy(_._2)
//    .view
//    .mapValues(_.map(_._1).reverse)
//    .toMap
//
//  val instrRe = raw"move (\d+) from (\d+) to (\d+)".r
//  val instrs = instrInput.map { case instrRe(count, target, dest) ⇒
//    Instr(count.toInt, target.toInt, dest.toInt)
//  }
//
//  Seq(false, true).foreach { is9001 ⇒
//    val stackTop = instrs
//      .foldLeft(stack) { case (stack, Instr(count, target, dest)) ⇒
//        val newTarget = stack(target).dropRight(count)
//        val toMove    = stack(target).takeRight(count)
//        val newDest   = stack(dest) ++ (if is9001 then toMove else toMove.reverse)
//        stack + (target → newTarget, dest → newDest)
//      }
//      .toVector
//      .sortBy(_._1)
//      .map(_._2.last)
//      .mkString
//
//    println(stackTop)
//  }
