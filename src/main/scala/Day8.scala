//case class Grid[T](grid: IndexedSeq[IndexedSeq[T]]):
//  private val (n, m)           = (grid.length, grid(0).length)
//  def apply(i: Int, j: Int): T = grid(i)(j)
//  def views(i: Int, j: Int): IndexedSeq[IndexedSeq[T]] =
//    Vector(
//      (0 until i).map(i ⇒ this(i, j)).reverse,
//      (i + 1 until n).map(i ⇒ this(i, j)),
//      (0 until j).map(j ⇒ this(i, j)).reverse,
//      (j + 1 until m).map(j ⇒ this(i, j))
//    )
//  def isEdge(i: Int, j: Int): Boolean = i == 0 || j == 0 || i == n - 1 || j == m - 1
//  def map[A](f: ((Int, Int), T) ⇒ A): Grid[A] = Grid {
//    grid.zipWithIndex.map { case (row, i) ⇒
//      row.zipWithIndex.map { case (t, j) ⇒
//        f((i, j), t)
//      }
//    }
//  }
//
//@main
//def day8 =
//  val input = Common.getInput(8).map(_.toVector.map(_.toString.toInt))
//  val grid  = Grid(input)
//  val visible = grid.map { case ((i, j), height) ⇒
//    !grid.views(i, j).forall(_.exists(_ >= height)) || grid.isEdge(i, j)
//  }
//  println(visible.grid.flatten.count(identity))
//
//  val scenic = grid.map { case ((i, j), height) ⇒
//    grid
//      .views(i, j)
//      .map { trees ⇒
//        val (l, r) = trees.span(_ < height)
//        r.headOption.map(l :+ _).getOrElse(l).size
//      }
//      .product
//  }
//
//  println(scenic.grid.flatten.max)
