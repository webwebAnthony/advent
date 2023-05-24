package advent.day_2

import zio.*
import zio.stream.*

import java.io.{FileInputStream, IOException}
import scala.util.matching.Regex
import Player.*
import Opponent.*
import Match.*

val home = java.lang.System.getProperty("user.home")

val stream = advent.getFileStream("input_2.txt").apply

object MainRun extends ZIOAppDefault {
  val partOne =
    stream
      .via(
        ZPipeline.utf8Decode >>>
          ZPipeline.splitOn("\n")
            .map(_.replace(" ", "")))
      .map(_.toList)
      .map({ case List(op, pl) => Match(Opponent.from(op), Player.from(pl)).apply})
      .runSum
      .debug

  val partTwo =
    stream
      .via(
        ZPipeline.utf8Decode >>>
          ZPipeline.splitOn("\n")
            .map(_.replace(" ", ""))
            .map(_.toList)
            .map({case List(op, pl) => Match(Opponent.from(op), Player.calculateMove(pl, Opponent.from(op))).apply }))
      .runSum
      .debug

  override def run = for {
    part1 <- partOne
    part2 <- partTwo
  } yield (part1, part2)

}
