package advent.day_1

import zio.*
enum Elf extends Product with Serializable:
  private case Food(val value: Int) extends Elf
  private case NoFood extends Elf

object Elf {

  def apply(input: String) = if(input.forall(Character.isDigit) && input != "") fromNumber(input.toInt) else fromNumber(0)

  private def fromNumber(in: Int) = in match {
    case value: Int if value > 0 => Food(value)
    case _ => NoFood
  }

}

object RunMain extends App {
  val number = "123"
  val number2 = ""
  val number3 = "0"
  val number4 = "12.21" // Doens't need to work with float

  private val why = Elf(number)

  println(why)


}

//  def compare(that: Elf[Nothing]) = this.Food >= that

//def get(in: Elf[Int]) = in.map(_)

//def get: Any = Elf[Nothing] match { case Food => Food.get }

//def apply(input: String) = ZIO.attempt(input.toInt).fold(
//  _ => fromNumber(0),
//  number => fromNumber(number)
//)
