import scala.collection.mutable

case class Path(elements: String*):
  def +(element: String) = Path(elements :+ element*)

case class Directory(
    path: Path,
    parent: Option[Directory],
    files: mutable.ArrayBuffer[File],
    dirs: mutable.Map[Path, Directory]
):
  def size: Int               = files.map(_.size).sum + dirs.map(_._2.size).sum
  def flatten: Seq[Directory] = dirs.values.toSeq.flatMap(_.flatten) :+ this

case class File(name: String, size: Int)

@main
def day7Mutable(): Unit =
  val input = Common.getInput(7)
  val cd    = raw"\$$ cd ([\w/\.]+)".r
  val dir   = raw"dir (\w+)".r
  val file  = raw"(\d+) ([\w\.]+)".r

  val root = Directory(Path("/"), None, mutable.ArrayBuffer.empty, mutable.Map.empty)
  var pwd  = root

  for elem <- input do
    elem match
      case cd("/")  ⇒ pwd = root
      case cd("..") ⇒ pwd = pwd.parent.get

      case cd(dir) ⇒
        val directory = pwd.dirs(pwd.path + dir)
        pwd = directory

      case file(size, name) ⇒
        pwd.files.addOne(File(name, size.toInt))

      case dir(dir) ⇒
        val path = pwd.path + dir
        pwd.dirs.put(path, Directory(path, Some(pwd), mutable.ArrayBuffer.empty, mutable.Map.empty))

      case _ ⇒

  val sizes = root.flatten.map(_.size)
  val sum   = sizes.filter(_ <= 100000).sum
  println(sum)

  val neededSpace = 30000000 - (70000000 - root.size)
  val smallest    = sizes.sorted.dropWhile(_ <= neededSpace).head
  println(smallest)
