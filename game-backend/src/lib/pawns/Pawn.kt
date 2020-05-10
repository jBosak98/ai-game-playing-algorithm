package lib.pawns

import model.Position
import lib.Team.Team

data class Pawn(
    val row:Int,
    val column:Int,
    val team: Team,
    val isKing: Boolean = false,
    var possibleMoves:List<Position> = listOf()
)