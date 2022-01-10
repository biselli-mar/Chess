/*                                                                                      *\
**     _________  ______________________                                                **
**    /  ___/  / /  /  ____/  ___/  ___/        2021 Emanuel Kupke & Marcel Biselli     **
**   /  /  /  /_/  /  /__  \  \  \  \           https://github.com/emanuelk02/Chess     **
**  /  /__/  __   /  /___ __\  \__\  \                                                  **
**  \    /__/ /__/______/______/\    /         Software Engineering | HTWG Constance    **
**   \__/                        \__/                                                   **
**                                                                                      **
\*                                                                                      */


package de.htwg.se.chess
package model
package gameDataComponent

import gameDataBaseImpl.ChessField
import model.Tile
import util.Matrix


/**
 * A data structure storing all information necessary to
 * run a game of chess.
 * Gamefield provides functional procedures which return a
 * modified version of itself, which also provide functionality for the needs of the
 * ControllerInterface.
 * 
 * GameField encapsulates some sort of Matrix representing a checker board
 * which contains all pieces of the game, along with any
 * functionality to store the state of the game:
     - wether the game is active 
     - wether and if which tile is selected by a controller using the field
     - which color's turn it is 
     - to which sides white and black can castle
     - counters for half-moves and full-moves
 *
 * It utilizes the Tile class to grant unified access for any higher hierarchy classes.
 * A typical use of the interface would look like this:
     val field = <Any Implementation of the Interface>

     // puts a black king into tile A1
     field.replace(Tile("A1"), "B_KING"))
     // move the piece in A1 to B2
     field.move(Tile("A1"), Tile("B2"))
 *
 * A standard factory may be used to get a standard base implementation
 * of a GameField: GameField()
 * 
 * @param field     Holds all the pieces on the board; None represents an empty tile
 * */
trait GameField (field: Matrix[Option[Piece]]) {
    /** Size of the board in rows **/
    val size = field.size

    /**
     * Gives the piece stored in field at given tile.
     * @param tile  Tile to get the piece from
     * @return      Some(Piece) if the tile in field is defined, None if not
     * */
    def cell(tile: Tile): Option[Piece]

    /**
     * Replaces the piece in field at the given tile with the provided filling.
     * @param tile  Tile to replace the contents of
     * @param fill  The piece which should replace the current one
     * @return      The same field but with the newly replaced piece
     * */
    def replace(tile: Tile, fill: Option[Piece]): GameField
    /**
     * Replaces the piece in field at the given tile with the provided filling.
     * @param tile  Tile to replace the contents of
     * @param fill  The string of the piece which should replace the current one
     * @return      The same field but with the newly replaced piece
     * */
    def replace(tile: Tile, fill: String):        GameField

    /**
     * Entirely fills the field with given piece.
     * @param filling  The piece which the board should be filled with
     * @return         The same field but with the newly filled board
     * */
    def fill(filling: Option[Piece]): GameField
    /**
     * Entirely fills the field with given piece.
     * @param filling  The string of the piece which the board should be filled with
     * @return         The same field but with the newly filled board
     * */
    def fill(filling: String):        GameField

    /**
     * Moves contents of one tile inte another tile.
     * If the source tile is empty the destination tile should
     * remain unchanged.
     * @param tile1     Source tile
     * @param tile2     Destination tile
     * @return          The same field but with the moved piece
     * */
    def move(tile1: Tile, tile2: Tile): GameField

    /**
     * Returns a list of all tiles the piece in given tile can move to.
     * Returned tiles are fully legal and respect check.
     * For empty tiles an empty list is returned.
     * @param tile      Source tile
     * @return          List of tiles which are legal to move to
     */
    def getLegalMoves(tile: Tile): List[Tile]
    
    /**
     * Loads a board position and game state from the given string.
     * Its format should follow the norms as specified here: https://www.chessprogramming.org/Forsyth-Edwards_Notation
     * @param fen   The FEN string
     * @return      A board instantiated by the specified FEN-String
     * */
    def loadFromFen(fen: String): GameField

    /**
     * Stores that a given tile has been selected.
     * If tile is None, any currently selected tile will be unselected
     * @param tile  Tile to select or None
     * @return      The same field but with the tile selected/unselected
     * */
    def select(tile: Option[Tile]): GameField
    /** Gives the currently selected tile, if there is one. @return Some(Tile) if a tile is selected or None, if not */
    def selected: Option[Tile]

    /** Starts the game. Prohibiting free placement and illegal moves. @return The same field but now in an active playing state */
    def start: GameField
    /** Stops the game. Returns to free placement and free moves. @return The same field but not in a playing state */
    def stop:  GameField
}

object GameField {
    def apply(): GameField = {
        ChessField()
    }
}