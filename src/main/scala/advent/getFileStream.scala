package advent

import zio.ZIO
import zio.stream.ZStream

import java.io.{FileInputStream, IOException}

case class getFileStream(fileName: String):
  val home = java.lang.System.getProperty("user.home")

  def apply: ZStream[Any, IOException, Byte] = ZStream.fromInputStreamZIO(
    ZIO.attempt(new FileInputStream(s"$home/projects/scala-playground/playground/src/main/resources/advent/2022/$fileName"))
      .refineToOrDie[IOException]
  )
