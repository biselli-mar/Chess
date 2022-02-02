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
package pieceStandardImpl

import pieceComponent.{Bishop => IBishop}
import gameDataComponent.GameField


class Bishop extends IBishop(( 1, 1) :: ( 1,-1) :: (-1, 1) :: (-1,-1) :: Nil)
