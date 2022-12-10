enum Instr:
  case NoOp
  case AddX(value: Int)

class CPU(output: Boolean):
  import scala.collection.mutable

  private var cycle   = 0
  private var x       = 1
  private val history = mutable.ArrayBuffer(x)

  def run(instr: Instr): Unit = instr match
    case Instr.NoOp ⇒ runCycle()
    case Instr.AddX(value) ⇒
      runCycle()
      runCycle()
      x += value

  def getHistory: Vector[Int] = history.toVector

  private def runCycle(): Unit =
    history.addOne(x)
    if output then outputCycle()
    cycle += 1

  private def outputCycle(): Unit =
    if cycle          % 40 == 0 then println()
    if x - 1 <= cycle % 40 && x + 1 >= cycle % 40 then print("#")
    else print(" ")

@main
def day10 =
  val addx = raw"addx ([-\d]+)".r
  val input = Common.getInput(10).map {
    case "noop"      ⇒ Instr.NoOp
    case addx(value) ⇒ Instr.AddX(value.toInt)
  }

  val cpu = CPU(output = false)
  input.foreach(cpu.run)

  val history  = cpu.getHistory
  val strength = Seq(20, 60, 100, 140, 180, 220).map(i ⇒ i * history(i)).sum
  println(strength)

  val outputCPU = CPU(output = true)
  input.foreach(outputCPU.run)
