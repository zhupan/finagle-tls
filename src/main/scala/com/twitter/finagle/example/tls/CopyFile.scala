package com.twitter.finagle.example.tls

import java.io._

/**
 * @author Panos Zhu
 *         Email panos.zhu@gmail.com
 */
object CopyFile {

  def copy(from: java.io.File, to: String) {
    mkdirsAndFile(to)
    val out = new java.io.BufferedWriter(new java.io.FileWriter(to))
    io.Source.fromFile(from).getLines().foreach(s => out.write(s, 0, s.length))
    out.close()
  }


  def mkdirsAndFile(filePathAndName: String) {
    val file = new File(filePathAndName)
    if (file.exists()) {
      return
    }
    val fileName = filePathAndName.substring(filePathAndName.lastIndexOf("/") + 1)
    val path = filePathAndName.substring(0, filePathAndName.indexOf(fileName))
    new File(path).mkdirs()
    file.createNewFile()
  }

  def use[T <: {def close()}](closable: T)(block: T => Unit) {
    try {
      block(closable)
    }
    finally {
      closable.close()
    }
  }

  def copy(from: String, to: String) {
    copy(new FileInputStream(from), to)
  }

  @throws(classOf[IOException])
  def copy(from: InputStream, to: String) {
    mkdirsAndFile(to)
    use(from) {
      in =>
        use(new FileOutputStream(to)) {
          out =>
            val buffer = new Array[Byte](1024)
            Iterator.continually(in.read(buffer)).takeWhile(_ != -1).foreach {
              out.write(buffer, 0, _)
            }
        }
    }
  }
}
