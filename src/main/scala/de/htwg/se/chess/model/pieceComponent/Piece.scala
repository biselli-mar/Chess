/*                                                                                      *\
**     _________  ______________________                                                **
**    /  ___/  / /  /  ____/  ___/  ___/                2022 Marcel Biselli             **
**   /  /  /  /_/  /  /__  \  \  \  \          https://github.com/biselli-mar/Chess     **
**  /  /__/  __   /  /___ __\  \__\  \                                                  **
**  \    /__/ /__/______/______/\    /         Software Engineering | HTWG Constance    **
**   \__/                        \__/                                                   **
**                                                                                      **
\*                                                                                      */


package de.htwg.se.chess
package model
package pieceComponent

import com.google.inject.Guice
import com.google.inject.AbstractModule

import scala.collection.mutable.Buffer
import scala.util.Try
import scala.util.Success
import scala.util.Failure

import gameDataComponent.GameField
import util.ChainHandler


trait PieceType(moveDirections: List[Tuple2[Int, Int]]):

  protected val legalMoveConditions: scala.collection.mutable.Seq[Tuple2[Tile, GameField] => Option[Tile]] = 
    Buffer(
      ( (tile, field) => if field.cell(tile).isDefined then None else Some(tile) ),
      ( (tile, field) => if field.cell(tile).get.getColor != field.color then Some(tile) else None )
    )

  protected val winningConditions: scala.collection.mutable.Seq[(Tile, Tile, GameField) => Boolean] = 
    Buffer(
      ( (srcTile, destTile, field) => 
          if field.getKingSquare.isDefined
              then !field.rawMove(srcTile, destTile)
                    .setColor(field.color)
                    .isAttacked(
                      if (field.cell(srcTile).get.getType.getClass == classOf[King]) 
                        then destTile
                        else field.getKingSquare.get
                    )
              else true
      )
    )

  protected def legalMoveConditionChain = ChainHandler[Tuple2[Tile, GameField], Tile](legalMoveConditions.toList)

  protected val gameFieldInjector = Guice.createInjector(new ChessModule)

  def move(srcTile: Tile, destTile: Tile, srcField: GameField): GameField
  def getLegalMoves(srcTile: Tile, srcField: GameField): List[Tile] = {
    computeMoves(srcTile, srcField).filter( destTile => winningConditions.forall(cond => cond(srcTile, destTile, srcField)))
  }
  def computeMoves(srcTile: Tile, srcField: GameField): List[Tile]


abstract class SlidingPiece(moveDirections: List[Tuple2[Int, Int]]) extends PieceType(moveDirections) {

  override def move(srcTile: Tile, destTile: Tile, srcField: GameField): GameField = {
    srcField.rawMove(srcTile, destTile)
  }
  override def computeMoves(srcTile: Tile, srcField: GameField): List[Tile] = {
    val ret = moveDirections.map( move =>
      var prevPiece: Option[model.Piece] = None
      for i <- 1 to srcField.size 
      yield {
        if (prevPiece.isEmpty) {
          Try(srcTile - (move(0)*i, move(1)*i)) match {
            case s: Success[Tile] => {
                prevPiece = srcField.cell(s.get)
                legalMoveConditionChain.handleRequest(s.get, srcField)
            }
            case f: Failure[Tile] => None
          }
        }
        else None
      }
    )
    ret.flatMap( x => x.takeWhile( p => p.isDefined)).map( x => x.get )
  }
}

abstract class NonSlidingPiece(moveDirections: List[Tuple2[Int, Int]]) extends PieceType(moveDirections) {

  override def move(srcTile: Tile, destTile: Tile, srcField: GameField): GameField = {
    srcField.rawMove(srcTile, destTile)
  }

  override def computeMoves(srcTile: Tile, srcField: GameField): List[Tile] = {
    moveDirections.filter( x => Try(srcTile - x).isSuccess )
                .filter( x => legalMoveConditionChain.handleRequest(srcTile - x, srcField).isDefined )
                .map( x => srcTile - x )
  }
}

abstract class Queen(moveDirections: List[Tuple2[Int, Int]]) extends SlidingPiece(moveDirections)
abstract class King(moveDirections: List[Tuple2[Int, Int]]) extends NonSlidingPiece(moveDirections)
abstract class Rook(moveDirections: List[Tuple2[Int, Int]]) extends SlidingPiece(moveDirections)
abstract class Bishop(moveDirections: List[Tuple2[Int, Int]]) extends SlidingPiece(moveDirections)
abstract class Knight(moveDirections: List[Tuple2[Int, Int]]) extends NonSlidingPiece(moveDirections)
abstract class Pawn(moveDirections: List[Tuple2[Int, Int]]) extends NonSlidingPiece(moveDirections)

object PieceType:
  var pieceCreator = Guice.createInjector(new StandardPieceModule)
  def setModule(pieceModule: AbstractModule): Unit = pieceCreator = Guice.createInjector(pieceModule)

  def Queen = pieceCreator.getInstance(classOf[Queen])
  def King = pieceCreator.getInstance(classOf[King])
  def Rook = pieceCreator.getInstance(classOf[Rook])
  def Bishop = pieceCreator.getInstance(classOf[Bishop])
  def Knight = pieceCreator.getInstance(classOf[Knight])
  def Pawn = pieceCreator.getInstance(classOf[Pawn])

enum PieceColor:
  case Black, White

  def invert: PieceColor = this.match {
    case Black => White
    case White => Black
  }

enum Piece(color: PieceColor, ptype: PieceType, name: String):
  case W_KING extends Piece(PieceColor.White, PieceType.King, "K")
  case W_QUEEN extends Piece(PieceColor.White, PieceType.Queen, "Q")
  case W_ROOK extends Piece(PieceColor.White, PieceType.Rook, "R")
  case W_BISHOP extends Piece(PieceColor.White, PieceType.Bishop, "B")
  case W_KNIGHT extends Piece(PieceColor.White, PieceType.Knight, "N")
  case W_PAWN extends Piece(PieceColor.White, PieceType.Pawn, "P")
  case B_KING extends Piece(PieceColor.Black, PieceType.King, "k")
  case B_QUEEN extends Piece(PieceColor.Black, PieceType.Queen, "q")
  case B_ROOK extends Piece(PieceColor.Black, PieceType.Rook, "r")
  case B_BISHOP extends Piece(PieceColor.Black, PieceType.Bishop, "b")
  case B_KNIGHT extends Piece(PieceColor.Black, PieceType.Knight, "n")
  case B_PAWN extends Piece(PieceColor.Black, PieceType.Pawn, "p")

  def getColor: PieceColor = color
  def getType: PieceType = ptype

  def move(srcTile: Tile, destTile: Tile, srcField: GameField): GameField = ptype.move(srcTile, destTile, srcField)
  def getLegalMoves(srcTile: Tile, srcField: GameField): List[Tile] = ptype.getLegalMoves(srcTile, srcField)

  override def toString: String = name

object Piece:
  def apply(piece: String): Option[Piece] = {
    piece.toUpperCase match {
      case "W_KING" | "W_QUEEN" | "W_ROOK" | "W_BISHOP" | "W_KNIGHT" |
          "W_PAWN" | "B_KING" | "B_QUEEN" | "B_ROOK" | "B_BISHOP" | "B_KNIGHT" |
          "B_PAWN" =>
        Some(Piece.valueOf(piece.toUpperCase))
      case _ =>
        val n = Piece.values.map(p => p.toString).indexOf(piece)
        if (n < 0) then None else Some(Piece.fromOrdinal(n))
    }
  }
  def apply(piece: Char): Option[Piece] = {
    val n = Piece.values.map(p => p.toString.toCharArray.apply(0)).indexOf(piece)
    if (n < 0) then None else Some(Piece.fromOrdinal(n))
  }
