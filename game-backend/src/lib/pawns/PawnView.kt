package lib.pawns

import model.Position
import lib.Team.Team

data class PawnView(
    val row:Int,
    val column:Int,
    val team: Team,
    val isKing: Boolean,
    val possibleMoves:List<Position>
)