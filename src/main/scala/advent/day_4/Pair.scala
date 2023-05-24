package advent.day_4
import Assignment.*

/**
 * Pair is a case class that holds two Assignment objects left and right.
 * @param l: Assignment
 * @param r: Assignment
 */
case class Pair(l: Assignment, r: Assignment):

  /**
   * getIntersection: Returns the intersection of two Assignment objects (left and right).
   * @return List[Int]
   */
  private def getIntersection: List[Int] =
    val lNumber = l.getBetween()
    val rNumber = r.getBetween()
    lNumber.intersect(rNumber)

  /**
   * isOverlapping: Returns a Boolean value indicating if left and right are overlapping
   * (i.e. if their intersection is not empty).
   * @return Boolean
   */
  def isOverlapping: Boolean =
    if(getIntersection.nonEmpty) true else false

  /**
   * isContained: Returns a Boolean value indicating if left is fully contained in right or vice versa.
   * @return Boolean
   */
  def isContained: Boolean =
    val lNumber = l.getBetween()
    val rNumber = r.getBetween()
    val interSect1 = lNumber.intersect(rNumber)
    val interSect2 = rNumber.intersect(lNumber)
    if(interSect1.length >= lNumber.length || interSect2.length >= rNumber.length) true else false

object Pair:
  /**
   * create: A factory method to create an instance of Pair class by passing two Assignment objects l and r as arguments.
   */
  def create(l: Assignment, r: Assignment): Pair =
    Pair(l, r)
