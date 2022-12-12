import scala.annotation.tailrec
import scala.collection.immutable.Queue

case class Grid(grid: Vector[Vector[Int]]):
  private val (n, m)    = (grid.length, grid(0).length)
  private val neighbors = Vector((0, 1), (0, -1), (-1, 0), (1, 0))

  def apply(i: Int, j: Int): Int = grid(i)(j)

  def bounds(i: Int, j: Int): Boolean =
    i >= 0 && i < n && j >= 0 && j < m

  def validMoves(i: Int, j: Int): Vector[(Int, Int)] =
    neighbors
      .map { case (ip, jp) ⇒ (ip + i, jp + j) }
      .filter { case (ip, jp) ⇒ bounds(ip, jp) && this(ip, jp) - this(i, j) <= 1 }

@main
def day12 =
  val input = Common.getInput(12).map(s ⇒ s.toCharArray.toVector)
  val Vector((is, js, _), (ie, je, _)) = input.zipWithIndex
    .map { case (s, i) ⇒
      s.zipWithIndex.map { case (c, j) ⇒ (i, j, c) }.filter { case (_, _, c) ⇒
        c == 'S' || c == 'E'
      }
    }
    .filter(_.nonEmpty)
    .flatten
  val grid = Grid {
    input.map(_.map {
      case 'S' ⇒ 0
      case 'E' ⇒ 25
      case c   ⇒ c - 'a'
    })
  }

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

  val min = grid.grid.zipWithIndex
    .map { case (s, i) ⇒
      s.zipWithIndex
        .map { case (height, j) ⇒ (i, j, height) }
        .filter { case (_, _, height) ⇒ height == 0 }
        .map { case (i, j, _) ⇒ (i, j) }
    }
    .filter(_.nonEmpty)
    .flatten
    .map { case (i, j) ⇒ bfs(Queue((i, j, 0)), Set((i, j))) }
    .min

  println(min)
