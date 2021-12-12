package de.htwg.se.chess
package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class TileSpec extends AnyWordSpec {
    "A Tile" should {
        "be created using Chess file and rank integers and the size of the corresponding ChessField" in {
            // Note that, in Chess ranks count from the white side upwards from 1 to 8
            // Files are described as Character from 'A' on the left to 'H' on the  right
            Tile(1, 1, 8) should be(new Tile(1, 1, 8))
        }
        "be created using the factory methods" in {
            Tile(List('A', '1'), 2) should be(new Tile(1, 1, 2))
            Tile("B1", 2) should be(new Tile(2, 1, 2))
            Tile(List('A','2')) should be(new Tile(1, 2, 8))
            Tile("B2") should be(new Tile(2, 2, 8))

            an [AssertionError] should be thrownBy Tile("A3", 2)
            an [AssertionError] should be thrownBy Tile("C2", 2)
        }
        "allow conversion to matrix collumns and rows" in {
            val tile1 = Tile("A1")
            tile1.col shouldBe 0
            tile1.row shouldBe 7 // 8 - 1

            val tile2 = Tile("B2", 2)
            tile2.col shouldBe 1
            tile2.row shouldBe 0 // 2 - 2
        }
        "allow conversions into Chess file and rank characters and to one String" in {
            val tile1 = Tile(1, 1, 8)
            tile1.fileChar shouldBe 'A'
            tile1.rankChar shouldBe '1'
            tile1.toString shouldBe "A1"

            val tile2 = Tile("B2", 2)
            tile2.fileChar shouldBe 'B'
            tile2.rankChar shouldBe '2'
            tile2.toString shouldBe "B2"
        }
    }
}