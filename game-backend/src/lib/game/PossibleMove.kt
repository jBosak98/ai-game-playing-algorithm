package lib.game

import lib.pawns.Pawns
import lib.position.Position

data class PossibleMove(
    val destination: Position,
    val captures: Int,
    val pawns: Pawns
)