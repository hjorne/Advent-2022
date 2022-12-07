case class Path(elements: String*):
  def +(element: String) = Path(elements :+ element*)
  def parent: Path       = Path(elements.dropRight(1)*)

case class Directory(path: Path, files: Seq[File], dirs: Seq[Path]):
  def addFile(file: File): Directory = this.copy(files = files :+ file)
  def addDir(dir: Path): Directory   = this.copy(dirs = dirs :+ dir)

  def size(directories: Map[Path, Directory]): Int =
    files.map(_.size).sum + dirs.map(directories(_).size(directories)).sum

case class File(name: String, size: Int)

@main
def day7 =
  val input = Common.getInput(7)
  val cd    = raw"\$$ cd ([\w/\.]+)".r
  val dir   = raw"dir (\w+)".r
  val file  = raw"(\d+) ([\w\.]+)".r
  val root  = Path("/")

  val (_, directories) = input.foldLeft((root, Map(root → Directory(root, Seq.empty, Seq.empty)))) {

    case ((_, directories), cd("/"))    ⇒ (root, directories)
    case ((pwd, directories), cd("..")) ⇒ (pwd.parent, directories)

    case ((pwd, directories), cd(dir)) ⇒
      val path      = pwd + dir
      val directory = directories.getOrElse(path, Directory(path, Seq.empty, Seq.empty))
      (path, directories + (path → directory))

    case ((pwd, directories), file(size, name)) ⇒
      val directory = directories(pwd).addFile(File(name, size.toInt))
      (pwd, directories + (pwd → directory))

    case ((pwd, directories), dir(dir)) ⇒
      val directory = directories(pwd).addDir(pwd + dir)
      (pwd, directories + (pwd → directory))

    case ((pwd, directories), "$ ls") ⇒ (pwd, directories)
  }

  val sizes = directories.view.mapValues(_.size(directories)).toMap
  val sum   = sizes.values.filter(_ <= 100000).sum
  println(sum)

  val neededSpace = 30000000 - (70000000 - sizes(root))
  val smallest =
    sizes.toList.sortBy(_._2).dropWhile { case (_, size) ⇒ size <= neededSpace }.head._2
  println(smallest)
