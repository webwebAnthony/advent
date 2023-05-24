package advent.day_4

import scala.collection.immutable.List

/**
 * Assignment is a case class that holds two integer values from and to.
 * @param from: Int
 * @param to: Int
 */
case class Assignment(from: Int, to: Int):
  /**
   * getBetween: Returns a list of integers between from and to inclusive.
   * @return List[Int]
   */
  def getBetween(): List[Int] =
    def inner(from: Int, to: Int, accum: List[Int]): List[Int] =
      if (accum.last == to || from > to) accum
      else inner(from, to, accum :+ (accum.last + 1))

    inner(from, to, List(from))

object Assignment:
  /**
   * create: A factory method to create an instance of Assignment class by passing a string in as an argument.
   * The string should be in the format "from-to".
   *
   * The method splits the string into two parts, converts them to integers and creates an instance of Assignment.
   * @param in: String
   * @return Assignment
   */
  def create(in: String): Assignment =
    val splitInput = in.split("-")
    Assignment(splitInput(0).toInt, splitInput(1).toInt)