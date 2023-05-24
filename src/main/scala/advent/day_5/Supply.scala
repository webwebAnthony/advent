package advent.day_5

import zio.*
import zio.Ref

import scala.io.AnsiColor.*
import scala.language.postfixOps

/*
 * stack(
 *   map(
 *     1 -> cargoHold(N,Z),
 *     2 -> cargoHold(D,C,M),
 *     ...
 *   )
 * )
*/
case class Stack(
                  mapItem: Map[Int, CargoHold]
                )

object Stack:
  def initial: Stack =
    Stack(Map.empty)

  /*
    instruction: move 1 from 2 to 1 ->
  */
  def moveCrate(stack: Stack, instruction: String) = ???

  private def updateMap(key: Int, value: CargoHold, stack: Ref[Stack]): Stack = {
    val updatedMap = stack.get.map(_.mapItem + (key -> value))
    stack.get.map(_.copy(mapItem = updatedMap))
  }


  def stateOfRef(ref: Ref[Stack]): UIO[Unit] = for {
    r <- ref.get
    _ <- Console.printLine(s"${RED}Map: (${r.mapItem.size} entries):${RESET} ${r.mapItem} \n\n\n"
      + "-" * 200 + s"${RESET}\n").orElse(ZIO.unit)
  } yield ()


  //TODO move .replaceAll("[\\[\\]]", "") back to CargoHold
  def fromStringUnsafe(key: Int, value: String, ref: Ref[Stack]) =
    if (value.replaceAll(" ", "").isEmpty) ref
    else
      val result = for {
        _ <- stateOfRef(ref)
        cargo <- ZIO.succeed(CargoHold.fromStringUnsafe(value))
        refBefore <- ref.get
        _ <- ZIO.succeed(updateMap(key, cargo, ref))
      } yield ref
      result



// Could also be List[Char]
case class CargoHold(
                      cargo: List[String]
                    )

object CargoHold:
  def fromStringUnsafe(input: String): CargoHold =
    if (isNumeric(input)) CargoHold(List.empty)
    else CargoHold(List.apply(input))


val isNumeric: String => Boolean = (i: String) => i.toIntOption match {
  case Some(value) => true
  case _ => false
}

/*
Example:
[D]
[N] [C]
[Z] [M] [P]
 1   2   3

PLAN:

Step 1 : Remove all [ ]
Step 2 : Read by row and count current elem for Map key. ((1, D)) | ((1, N)(2, C)) | ((1, Z)(2, M)(3, P))
Step 3 : In the Stack companion object def instruction(count: Int, fromKey: Int, toKey: Int)
                                              ...
*/

import zio.*

object MainSupply extends ZIOAppDefault:
  val programm = for {
    ref <- Ref.make(Stack.initial)
    _ <- ZIO.succeed(Stack.fromStringUnsafe(1, "[D]", ref))
    _ <- ZIO.succeed(Stack.fromStringUnsafe(1, "[N]", ref))
    _ <- ZIO.succeed(Stack.fromStringUnsafe(1, "[Z]", ref))
    _ <- ZIO.succeed(Stack.fromStringUnsafe(2, "[C]", ref))
    _ <- ZIO.succeed(Stack.fromStringUnsafe(2, "[M]", ref))
    _ <- ZIO.succeed(Stack.fromStringUnsafe(3, "[P]", ref))
    _ <- Stack.stateOfRef(ref)
  } yield ()

  override def run =
    programm.exit


