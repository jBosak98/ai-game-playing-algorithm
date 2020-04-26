package algorithm

import lib.pawns.Pawn
import lib.pawns.getPawns
import lib.pawns.isFieldOccupied
import model.Position
import model.Team

fun getPossibleMoves(pawns:HashMap<Position, Pawn>, team:Team?): HashMap<Position,List<Position>> {
     return pawns
         .getPawns(team)
         .map {
             val moves =
                 getDiagonalMovesPositions(it.value)
                 .filter { position -> isPositionCorrect(position) && !pawns.isFieldOccupied(position) }
             it.key to moves
         }
         .toMap() as HashMap<Position, List<Position>>
}

fun getDiagonalMovesPositions(pawn: Pawn): List<Position> {
    val row = if(pawn.team === Team.BLACK) pawn.row + 1 else pawn.row - 1
    return when (pawn.isKing){
        true -> listOf(
            Position(pawn.row - 1, pawn.column - 1),
            Position(pawn.row - 1, pawn.column + 1),
            Position(pawn.row + 1, pawn.column - 1),
            Position(pawn.row + 1, pawn.column + 1)
        )
        false -> listOf(
            Position(row, pawn.column - 1),
            Position(row, pawn.column + 1)
        )
    }
}

fun isPositionCorrect(position: Position): Boolean =
     position.column in 0..7 && position.row in 0..7



