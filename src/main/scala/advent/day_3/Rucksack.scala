package advent.day_3

import scala.language.postfixOps
import advent.day_3.Compartment.*
import zio.schema.CaseSet.Empty

import scala.:+
import scala.annotation.tailrec

trait Priorities:
  val lowerCasePrio: Map[String, Int] =
    Map("a" -> 1, "b" -> 2, "c" -> 3, "d" -> 4, "e" -> 5, "f" -> 6, "g" -> 7, "h" -> 8, "i" -> 9, "j" -> 10, "k" -> 11,
      "l" -> 12, "m" -> 13, "n" -> 14, "o" -> 15, "p" -> 16, "q" -> 17, "r" -> 18, "s" -> 19, "t" -> 20, "u" -> 21,
      "v" -> 22, "w" -> 23, "x" -> 24, "y" -> 25, "z" -> 26)
  val upperCasePrio: Map[String, Int] =
    Map("A" -> 27, "B" -> 28, "C" -> 29, "D" -> 30, "E" -> 31, "F" -> 32, "G" -> 33, "H" -> 34, "I" -> 35, "J" -> 36, "K" -> 37,
      "L" -> 38, "M" -> 39, "N" -> 40, "O" -> 41, "P" -> 42, "Q" -> 43, "R" -> 44, "S" -> 45, "T" -> 46, "U" -> 47,
      "V" -> 48, "W" -> 49, "X" -> 50, "Y" -> 51, "Z" -> 52)

case class ElfGroup(elf_one: Rucksack, elf_two: Rucksack, elf_three: Rucksack) extends Priorities {

  def apply = getPriorities(checkForDuplicates)

  private def checkForDuplicates = {
    val diff = List.empty[Item]
    @tailrec
    def getItems(e1: List[Item], e2: List[Item], e3: List[Item], newList: List[Item]): List[Item] = {
      val innerDiff = List.empty[Item]
      if (e3.isEmpty)
        newList
      else if (e1.contains(e2.head))
        @tailrec
        def getItemsInner(inner1: List[Item], inner2: List[Item], newListInner: List[Item]): List[Item] = {
          if(inner2.isEmpty)
            println(newListInner)
            newListInner
          else if(inner1.contains(inner2.head))
            getItemsInner(inner1, inner2.tail, newListInner :+ inner2.head)
          else
            getItemsInner(inner1, inner2.tail, newListInner)
        }
        newList :+ getItemsInner(e1, e3.tail, innerDiff).head
      else
        getItems(e1, e2.tail, e3, newList)
    }

    getItems((elf_one.left ++ elf_one.right), (elf_two.left ++ elf_two.right), (elf_three.left ++ elf_three.right), diff)
  }

  private def getPriorities(input: List[Item]) = {
    val sumList = List.empty[Int]
    sumList :+ input.distinct.map(elem => if (elem.character.isUpper) upperCasePrio(elem.character.toString) else lowerCasePrio(elem.character.toString)).sum
  }
}

case class GroupManager(input: String) {
  def fromSeq = input.split(",").toSeq match {
    case Seq(str: String, str2: String, str3: String) => ElfGroup(RucksackBuilder(str).apply,RucksackBuilder(str2).apply,RucksackBuilder(str3).apply).apply
  }

  def apply = input.split(",").toList match {
    case List(str: String, str2: String, str3: String) => ElfGroup(RucksackBuilder(str).apply,RucksackBuilder(str2).apply,RucksackBuilder(str3).apply).apply
  }
}

case class Rucksack(left: List[Item], right: List[Item]) extends Priorities:

  def apply =
    this.getPriorities(checkForDuplicates)

  private def checkForDuplicates: List[Item] = {
    val diff = List.empty[Item]

    @tailrec
    def getItems(l: List[Item], r: List[Item], newList: List[Item]): List[Item] = {
      if (r.isEmpty)
        newList
      else if (l.contains(r.head))
        getItems(l, r.tail, newList :+ r.head)
      else
        getItems(l, r.tail, newList)
    }

    getItems(left, right, diff).distinct
  }

  private def getPriorities(input: List[Item]) = {
    val sumList = List.empty[Int]
    sumList :+ input.distinct.map(elem => if (elem.character.isUpper) upperCasePrio(elem.character.toString) else lowerCasePrio(elem.character.toString)).sum
  }

object Rucksack {
  def unapplySeq(left: List[Item], right: List[Item]): Option[Seq[Item]] = {
    if (left == Empty() && right == Empty()) Some(Seq.empty)
    else unapplySeq(left.head +: left.tail,right.head +: right.tail)
  }
}

class RucksackBuilder(input: String):
  private def getMiddle = input.length / 2

  private def getParts(mid: Int) = List(input.substring(0, mid), input.substring(mid))

  def apply: Rucksack =
    getParts(getMiddle) match {
      case List(left, right) => Rucksack(Compartment.from(left.toList), Compartment.from(right.toList))
      case _ => throw new MatchError("Error")
    }

enum Compartment extends Product with Serializable:
  case Item(character: Char) extends Compartment

object Compartment:
  def from(in: List[Char]): List[Item] =
    in.map(Item.apply)


object Test extends App:
  val advent_input_p1 = List(
    "vJrwpWtwJgWrhcsFMMfFFhFp",
    "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
    "PmmdzqPrVvPwwTWBwg",
    "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
    "ttgJtRGJQctTZtZT",
    "CrZsJsPPZsGzwwsLwLmpwMDw")
  val advent_input_p2 = "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn,nttgJtRGJQctTZtZT,nCrZsJsPPZsGzwwsLwLmpwMDw"
  println(s"Part one: ${advent_input_p1.map(in => RucksackBuilder(in).apply.apply)}")
  println(s"Part two (manually):${
    ElfGroup(RucksackBuilder("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn").apply, RucksackBuilder("ttgJtRGJQctTZtZT").apply, RucksackBuilder("CrZsJsPPZsGzwwsLwLmpwMDw").apply).apply
  }")
  println(s"Part two (manager):${GroupManager(advent_input_p2).apply}")
