package lib.game

import lib.pawns.Pawn
import lib.position.Position

data class Move(
    val pawn: Pawn,
    val destination: Position
)