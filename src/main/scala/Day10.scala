enum Instr:
  case NoOp
  case AddX(value: Int)

class CPU:
  import scala.collection.mutable

  private var ticker  = 0
  private var x       = 1
  private val history = mutable.ArrayBuffer(x)
  private val stdout  = mutable.ArrayBuffer.empty[Char]

  def run(instr: Instr): Unit = instr match
    case Instr.NoOp ⇒ tick(1)
    case Instr.AddX(value) ⇒
      tick(2)
      x += value

  def getHistory: Vector[Int] = history.toVector
  def getOutput: String       = stdout.mkString

  private def tick(count: Int): Unit =
    for _ ← 0 until count do
      output()
      history.addOne(x)
      ticker += 1

  private def output(): Unit =
    if ticker % 40 == 0 && ticker > 0 then stdout.addOne('\n')

    if x - 1 <= ticker % 40 && x + 1 >= ticker % 40 then stdout.addOne('#')
    else stdout.addOne(' ')

@main
def day10 =
  val addx = raw"addx ([-\d]+)".r
  val cpu  = CPU()
  Common.getInput(10).foreach {
    // CPUs only like ADTs, no strings allowed
    case "noop"      ⇒ cpu.run(Instr.NoOp)
    case addx(value) ⇒ cpu.run(Instr.AddX(value.toInt))
  }
  val history  = cpu.getHistory
  val strength = Seq(20, 60, 100, 140, 180, 220).map(i ⇒ i * history(i)).sum
  println(strength)
  println(cpu.getOutput)
