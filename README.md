# **Chess**

![Build_Status](https://github.com/emanuelk02/Chess/actions/workflows/scala.yml/badge.svg?branch=main)
[![Coverage Status](https://coveralls.io/repos/github/emanuelk02/Chess/badge.svg)](https://coveralls.io/github/emanuelk02/Chess)

## *README is a work in progress!*

---

## Table of Contents

| **Feature** | **Content** | **Code** | **Tests** |
| :------     | :------     |   -----: |   ------: |
| [Textual User Interface](#using-the-tui) | [Usage](#see-also-command-help-for-more-information) | [TUI.scala](src/main/scala/de/htwg/se/chess/aview/TUI.scala) | [TUISpec.scala](src/test/scala/de/htwg/se/chess/aview/TUISpec.scala) |
| | [Format](#board-representation) | [ChessBoard.scala](src/main/scala/de/htwg/se/chess/model/ChessBoard.scala) | [ChessBoardSpec.scala](src/test/scala/de/htwg/se/chess/model/ChessBoardSpec.scala) |

---

## Using the **TUI**

The text interface allows for following commands and inputs:

| Syntax | Description |
| :---------- | :---------: |
| **insert** | Inserts a piece into the matrix |
| **move** | Moves an already inserted piece to another location |
| **clear** | Clears all pieces from board |
| **fen** | Loads a Chess position from a given FEN-String |    

---

### Valid input

#### Inserting Pieces

 1. destination tile:
 consists of its file described by a char ('A' to 'H')
 and its rank described by an integer (1 to 8)
 2. desired piece:
 string describing a valid [piece](#inputs-for-pieces)

#### Moving Pieces

 1. source
---

### Inputs for Pieces

| **Piece** | **String** | **Alt.** |
| :-------  | :--------  | :------  |
| Black King | B_KING | k |
| Black Queen | B_QUEEN | q |
| Black Rook | B_ROOK | r |
| Black Bishop | B_BISHOP | b |
| Black Knight | B_KNIGHT | n |
| Black Pawn | B_PAWN | p |
| White King | W_KING | K |
| White Queen | W_QUEEN | Q |
| white Rook | W_ROOK | R |
| White Bishop | W_BISHOP | B |
| White Knight | W_KNIGHT | N |
| White Pawn | W_PAWN | P |

---

### See also command *help* for more information:

```
    Usage: <command> [options]
    Commands:
    help [command]      show this help message
                          
    i / insert / put <tile: "A1"> <piece>
                        inserts given piece at given tile
                        valid piece representations are:
                          - a color: 
                            W / B
                          - followed by an underscore and its type:
                            W/B_KING / QUEEN / ROOK / BISHOP / KNIGHT / PAWN
                        or
                          - their representations as in the FEN representation:
                            uppercase for white / lowercase for black:
                            King: K/k, Queen: Q/q, Rook: R/r,
                            Bishop: B/b, Knight: N/n, Pawn: P/p
                                              
    m / move <tile1: "A1"> <tile2: "B2">
                        moves piece at position of tile1 to the position of tile2

    cl / clear          clears entire board

    fen / FEN / Fen / loadFEN <fen-string>
                        initializes a chess position from given FEN-String

    exit                quits the program
```

---

### Board Representation

Game runs on console by printing an 8x8 matrix of boxes with letters - representing Chess pieces - inside them:

```
+---+---+---+---+---+---+---+---+
| r | n | b | q | k | b | n | r |
+---+---+---+---+---+---+---+---+
| p | p | p | p | p | p | p | p |
+---+---+---+---+---+---+---+---+
|   |   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+
|   |   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+
|   |   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+
|   |   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+
| P | P | P | P | P | P | P | P |
+---+---+---+---+---+---+---+---+
| R | N | B | Q | K | B | N | R |
+---+---+---+---+---+---+---+---+
```

The pieces match the representation in the **[Forsyth-Edwards Notation (FEN)](https://www.chessprogramming.org/Forsyth-Edwards_Notation)**.

---

 - **Uppercase** letters represent **white** pieces
 - **Lowercase** letters represent **black** pieces

Mappings:
 - **'K'** = *King*
 - **'Q'** = *Queen*
 - **'R'** = *Rook*
 - **'B'** = *Bishop*
 - **'N'** = *Knight*
 - **'P'** = *Pawn*

---



