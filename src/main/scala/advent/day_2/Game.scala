package advent.day_2

import advent.day_2.GameState.getPoints

/**
 * Could abstract away the moves because they are the same for Player and Opponent
 *
 *enum Move(val input: String) extends Product with Serializable:
 *  case Rock extends Move("Rock")
 *  case Paper extends Move("Paper")
 *  case Scissors extends Move("Scissors")
 *
 *object Move:
 *  def from(in: String): Move = in match
 *    case "Rock" => Move.Rock
 *    case "Paper" => Move.Paper
 *    case "Scissors" => Move.Scissors
*/

enum Opponent(val input: Char) extends Product with Serializable:
  case Rock extends Opponent('A')
  case Paper extends Opponent('B')
  case Scissors extends Opponent('C')

object Opponent:
  def from(in: Char): Opponent = in match
    case 'A' => Opponent.Rock
    case 'B' => Opponent.Paper
    case 'C' => Opponent.Scissors

enum Player(val input: Char)extends Product with Serializable:
  case Rock extends Player('X')
  case Paper extends Player('Y')
  case Scissors extends Player('Z')

object Player:

  def from(in: Char): Player = in match
    case 'X' => Player.Rock
    case 'Y' => Player.Paper
    case 'Z' => Player.Scissors

  def getPoints(in: Player): Int = in match
    case Rock => 1
    case Paper => 2
    case Scissors => 3

  /**
   * X => Loss <br>
   * Y => Draw <br>
   * Z => Win <br>
   */
  def calculateMove(in: Char, opponent: Opponent): Player = in match
    case 'X' => opponent match
      case Opponent.Rock => Player.Scissors
      case Opponent.Paper => Player.Rock
      case Opponent.Scissors => Player.Paper
    case 'Y' => opponent match
      case Opponent.Rock => Player.Rock
      case Opponent.Paper => Player.Paper
      case Opponent.Scissors => Player.Scissors
    case 'Z' => opponent match
      case Opponent.Rock => Player.Paper
      case Opponent.Paper => Player.Scissors
      case Opponent.Scissors => Player.Rock


enum GameState(val input: String) extends Product with Serializable:
  case Win extends GameState("W")
  case Loss extends GameState("L")
  case Draw extends GameState("D")

object GameState {
  def getPoints(in: GameState): Int = in match
    case Win => 6
    case Draw => 3
    case Loss => 0
}

case class Match(op: Opponent, pl: Player){
  def apply: Int = getPoints(calculateWinner) + Player.getPoints(pl)

  private def calculateWinner: GameState = pl match
    case Player.Rock => {
    if (op == Opponent.Rock) GameState.Draw
    else if(op == Opponent.Paper) GameState.Loss
    else GameState.Win
    }
    case Player.Paper => {
      if(op == Opponent.Rock) GameState.Win
      else if(op == Opponent.Paper) GameState.Draw
      else GameState.Loss
    }
    case Player.Scissors => {
      if(op == Opponent.Rock) GameState.Loss
      else if(op == Opponent.Paper) GameState.Win
      else GameState.Draw
    }

}

object Main extends App {
  println("Left is Opponent // Right is Player")
  println(s"R v P: ${Match(Opponent.Rock, Player.Paper).apply}")
  println(s"R v R: ${Match(Opponent.Rock, Player.Rock).apply}")
  println(s"R v S: ${Match(Opponent.Rock, Player.Scissors).apply}")
  println(s"P v R: ${Match(Opponent.Paper, Player.Rock).apply}")
  println(s"S v P: ${Match(Opponent.Scissors, Player.Paper).apply}")
  println(s"P v S: ${Match(Opponent.Paper, Player.Scissors).apply}")
}