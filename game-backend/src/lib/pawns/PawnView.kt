package lib.pawns

import model.Position
import model.Team

data class PawnView(
    val row:Int,
    val column:Int,
    val team: Team,
    val isKing: Boolean,
    val possibleMoves:List<Position>
)