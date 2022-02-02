trait PieceType

object PieceType:
  def Queen: PieceType = new Queen

class Queen extends PieceType

enum PieceColor:
  case Black, White

  def invert: PieceColor = this.match {
    case Black => White
    case White => Black
  }

enum Piece(color: PieceColor, ptype: PieceType, name: String):
  case W_QUEEN extends Piece(PieceColor.White, PieceType.Queen, "Q")

  def getColor: PieceColor = color
  def getType: PieceType = ptype

  override def toString: String = name


import Piece._
