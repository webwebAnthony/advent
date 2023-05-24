package advent.day_4
import zio.*
import zio.stream.*
import Assignment.*
import Pair.*

import java.io.IOException
import scala.language.postfixOps

val stream = advent.getFileStream("input_4.txt").apply

/**
 * The creation could be nicer instead of accessing the array keys 0 // 1
 *
 * A wrapper for the Chunk[Boolean] could be made i.E. case class Camp[input: List[Pairs]]
 * that contains a method to filter/count the true values.
 *
 * ```
 * def getCount() = input.filter(x => x == true).length
 *  ```
 */

val partOne =
  stream
    .via(ZPipeline.utf8Decode >>>
      ZPipeline.splitLines.map(_.split(",")
    ).map(arr =>
    Pair.create(Assignment.create(arr(0)), Assignment.create(arr(1))
  )).map(_.isContained)
).runCollect

val partTwo =
  stream
    .via(ZPipeline.utf8Decode >>>
      ZPipeline.splitLines.map(_.split(",")
      ).map(arr =>
        Pair.create(Assignment.create(arr(0)), Assignment.create(arr(1))
        )).map(_.isOverlapping)
    ).runCollect

object Main_4 extends ZIOAppDefault {
  override def run =

  /**
    * Simpler solution:
    *  count will sum the given condition where as everything is already a boolean so it just need to be called thats why `x => x`
    *                                       |
    *                                       |
    *                                       v
    * partOne.map(chun => chun.toList.count(x => x)).debug
    **/
    for {
      c1 <- partOne.map(chun => chun.toList.filter(x => x == true).length)
      c2 <- partTwo.map(chun => chun.toList.filter(x => x == true).length)
      _ <- Console.printLine(s"Part one: $c1")
      _ <- Console.printLine(s"Part two: $c2")
    } yield ()
}