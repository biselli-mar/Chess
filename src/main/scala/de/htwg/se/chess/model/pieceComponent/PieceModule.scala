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

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

import de.htwg.se.chess.model.pieceComponent.PieceType.Queen


class StandardPieceModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[Queen]).to(classOf[pieceStandardImpl.Queen])
    bind(classOf[King]).to(classOf[pieceStandardImpl.King])
    bind(classOf[Rook]).to(classOf[pieceStandardImpl.Rook])
    bind(classOf[Bishop]).to(classOf[pieceStandardImpl.Bishop])
    bind(classOf[Knight]).to(classOf[pieceStandardImpl.Knight])
    bind(classOf[Pawn]).to(classOf[pieceStandardImpl.Pawn])
  }
}