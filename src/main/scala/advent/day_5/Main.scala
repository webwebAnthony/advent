package advent.day_5
import zio.*
import zio.stream.*
import Stack.*

import java.io.IOException
import scala.language.postfixOps

val stream = advent.getFileStream("input_5_cargo.txt").apply
val instructionStream = advent.getFileStream("input_5.txt").apply
var count = 0

val stack = Stack.initial

val partOne = ???
/*  stream
    .via(ZPipeline.utf8Decode >>> ZPipeline.splitLines.take(3).map(line => Stack.fromStringUnsafe(count, line, stack))).runCollect*/


object Main_5 extends ZIOAppDefault:
  override def run = ???
    /*for {
      s <- partOne
      _ <- Console.printLine(s)
    } yield ()*/