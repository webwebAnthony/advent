package advent.day_3


import zio.*
import zio.stream.*
import Rucksack.*

import java.io.IOException
import scala.language.postfixOps

val stream = advent.getFileStream("input_3.txt").apply

val partOne =
  stream
    .via(ZPipeline.utf8Decode >>>
      ZPipeline.splitOn("\n"))
    .map(RucksackBuilder(_).apply.apply).map(_.sum).runFold(0) { (x, y) => x + y }.debug

def partTwo = {
  val stream = advent.getFileStream("input_3.txt").apply
  def inner(stream: ZStream[Any, IOException, Byte], acc: Chunk[List[Int]]): Any = {
    if (stream == ZStream.empty) acc
    else {
      stream
        .via(ZPipeline.utf8Decode
          >>> ZPipeline.splitOn("\n")
          .take(3).map(_.mkString(",")).map(line => GroupManager(line).apply))
        .run(ZSink.collectAll.collectLeftover.map { (res, leftover) => inner(ZStream.fromChunk(leftover), res) })
    }
  }

  inner(stream, Chunk(List(0)))
}

object Main extends ZIOAppDefault {
  override def run =
    Console.printLine(partTwo)



}