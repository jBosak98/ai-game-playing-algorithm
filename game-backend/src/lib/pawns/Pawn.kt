package lib.pawns

import lib.position.Position
import lib.Team.Team

data class Pawn(
    val row:Int,
    val column:Int,
    val team: Team,
    val isKing: Boolean = false,
    var possibleMoves:List<Position> = listOf()
)