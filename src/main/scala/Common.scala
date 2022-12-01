import scala.io.Source
import scala.util.Using

object Common: 
  def getInput(i: Int): Vector[String] =
    Using(Source.fromFile(s"input/day$i.txt"))(_.getLines.toVector).get

  def getInputString(i: Int): String =
    Using(Source.fromFile(s"input/day$i.txt"))(_.mkString).get