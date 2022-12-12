import scala.annotation.tailrec
import scala.collection.immutable.Queue

case class Grid(grid: Vector[Vector[Char]]):
  private val (n, m)    = (grid.length, grid(0).length)
  private val neighbors = Vector((0, 1), (0, -1), (-1, 0), (1, 0))

  def apply(i: Int, j: Int): Int = grid(i)(j) match
    case 'S' ⇒ 'a'
    case 'E' ⇒ 'z'
    case c   ⇒ c

  def bounds(i: Int, j: Int): Boolean =
    i >= 0 && i < n && j >= 0 && j < m

  def validMoves(i: Int, j: Int): Vector[(Int, Int)] =
    neighbors
      .map { case (ip, jp) ⇒ (ip + i, jp + j) }
      .filter { case (ip, jp) ⇒ bounds(ip, jp) && this(ip, jp) - this(i, j) <= 1 }

  def find(value: Char): Vector[(Int, Int)] =
    grid.zipWithIndex.flatMap { case (s, i) ⇒
      s.zipWithIndex.collect { case (v, j) if v == value ⇒ (i, j) }
    }

@main
def day12 =
  val input    = Common.getInput(12).map(s ⇒ s.toCharArray.toVector)
  val grid     = Grid(input)
  val (is, js) = grid.find('S').head
  val (ie, je) = grid.find('E').head

  @tailrec
  def bfs(queue: Queue[(Int, Int, Int)], seen: Set[(Int, Int)]): Int =
    queue.dequeueOption match
      case Some((i, j, count), newQueue) ⇒
        if i == ie && j == je then count
        else
          val moves      = grid.validMoves(i, j).filter(!seen.contains(_))
          val newerQueue = newQueue.enqueueAll(moves.map { case (i, j) ⇒ (i, j, count + 1) })
          bfs(newerQueue, seen ++ moves)
      case None ⇒
        Int.MaxValue

  println(bfs(Queue((is, js, 0)), Set((is, js))))

  val min = grid.find('a').map { case (i, j) => bfs(Queue((i, j, 0)), Set((i, j))) }.min

  println(min)
