package advent.day_1

import advent.day_1.Elf
import advent.day_1.Elf.*
import zio.*
import zio.stream.*

import java.io.{BufferedReader, FileInputStream, IOException}
import java.nio.charset.CharacterCodingException
import java.nio.file.Files
import scala.io.Source

object Main extends ZIOAppDefault {

  val initialElf = Elf.apply("1")
  val home = java.lang.System.getProperty("user.home")

  val stream = advent.getFileStream("input_1.txt").apply

  def program = stream.via(ZPipeline.utf8Decode >>>
    ZPipeline.splitOn("\u000a\u000a")
      .map(_.split("\u000a")
        .map(_.toInt))).map(row => row.sum).map(it => Elf(it.toString)).runCollect

  /**
   * Just a a regular list => Working
   *
   *
   * def program = stream.via(ZPipeline.utf8Decode >>>
   * ZPipeline.splitOn("\u000a\u000a")
   * .map(_.split("\u000a")
   * .map(_.toInt))).map(row => row.sum).runCollect
   */


  override def run = program.map(_.toList).debug
  //  override def run = program.map(_.toList.sorted.reverse.take(3).sum).debug
}

/**
 * Various attempts
 * //  override def run = stream.split(byte => byte.toString == "").runCollect.debug
 *
 * //  override def run = stream.mapChunks(chunk => chunk.split(it.toString => it == "")).runCollect.debug
 *
 * //  override def run = stream.mapChunks(chunk => chunk.map(it => it.toString).split(_ = "")).runCollect.debug
 *
 * //  override def run = stream.map(byte => byte.toChar).map(byte => byte.toString).collect {
 *
 * //  case digit: Int => digit
 * //}.runCollect.debug
 *
 * //   override def run = stream.map(byte => byte.toChar).map(byte => byte.toString).map{byteString => byteString.toInt}.runCollect.debug
 *
 * //   override def run = stream.via(ZPipeline.utf8Decode).flatMap(chunk => chunk.split(_ == "").map(_.tapEach(it => Console.printLine(it.chars()))).take(10)).runCollect.debug
 *
 * //  override def run = stream.via(ZPipeline.utf8Decode).map(la => la.toCharArray).split(_ == '0').map(_.toList).runCollect.debug
 *
 * //  override def run = stream.via(ZPipeline.utf8Decode).split(_ == "\u0000").runCollect.debug
 *
 * //  override def run = stream.map(byte => byte.toChar).map(byte => byte.toString).split(_ == "\u0000").runCollect.debug
 *
 * //  override def run = stream.map(byte => byte.toChar).map(byte => byte.toString).split(_.toByte == "\u0000".toByte).runCollect.debug
 *
 * //  override def run = stream.map(byte => byte.toChar).map(char => char.toString).filter(_ != ",").runCollect.debug
 *
 * //  override def run = stream.via(ZPipeline.utf8Decode >>> ZPipeline.splitOnChunk(chunk.toString: String => chunk == "\u0000")).take(60).runCollect.debug
 *
 * //OLD Almost there
 * //  override def run =
 * //    programm.repeatUntil(_.!=(NonEmptyChunk)).tap(that => Console.printLine(that)).exitCode
 *
 * //  override def run = programm.map(elem => elem match {
 * //    case int: Int => Console.printLine("IVE GOT AN INT")
 * //    case _ => Console.printLine("Its trash")
 * //  }).exitCode
 *
 * //  val yikes =
 * //  stream.via(ZPipeline.utf8Decode >>> ZPipeline.splitLines).run(ZSink.collectAllWhile(_ != ""))
 * //  stream.via(ZPipeline.utf8Decode >>> ZPipeline.splitLines).runFold(
 * //    case it
 * //  )
 *
 * //override def run = stream.runFold(initialElf)(it => Console.printLine(it), that => Console.printLine("that"))
 *
 * override def run = stream.via(ZPipeline.utf8Decode >>> ZPipeline.splitOn("\n\n") >>> ZPipeline.collect {
 * case it: Int => Elf(it.toString)
 * case that: String => Elf(that)
 * }).run(ZSink.collectAllToSet.map(that => that.mkString).map(it => it.toString)).debug
 *
 *
 * override def run = stream.via(ZPipeline.utf8Decode >>> ZPipeline.splitOn("\n\n") >>> ZPipeline.splitOn("\n").mapChunks(someCh => someCh.map(r => r.toInt).map(zt => Elf(zt.toString)))).runCollect.debug
 *
 *
 * override def run = stream.via(ZPipeline.utf8Decode >>> ZPipeline.splitOn("\n\n") >>> ZPipeline.splitOn("\n").collect {
 * case in: String => Elf(in)
 * }).runCollect.debug
 *
 *
 * def almost = stream.via(ZPipeline.utf8Decode >>>
 * ZPipeline.splitOn("\n\n")
 * .map(_.split("\n")
 * .map(_.toInt)
 * .map(that => Elf(that.toString)))).runCollect.tap(that => Console.printLine(that)).exitCode
 *
 * override def run = program.zipWith(ZIO.foreach) {
 * case(Elf(_) => Console.printLine(Elf(_)))}
 *
 * Trying to implement a sort function
 * import Elf.*
 * def sortLogic(e1: Elf[Int], e2: Elf[Int]) = {
 * e1.toString.length > e2.toString.length
 * }
 *
 *
 * override def run = program.map(_.toList.sortWith(sortLogic(initialElf, _))).debug
 */