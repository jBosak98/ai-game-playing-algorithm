package algorithm

import lib.Team.Team
import lib.pawns.Pawn
import lib.position.Position
import lib.position.isCorrect

fun getDiagonalMovesPositions(pawn: Pawn): List<Position> =
    getDiagonalMovesPositions(Position(pawn.row, pawn.column), pawn.team, pawn.isKing)

fun getDiagonalMovesPositions(position: Position, team: Team, isKing: Boolean = false): List<Position> {
    val row = position.row + team.teamDirection
    return when (isKing) {
        true -> listOf(
            Position(position.row - 1, position.column - 1),
            Position(position.row - 1, position.column + 1),
            Position(position.row + 1, position.column - 1),
            Position(position.row + 1, position.column + 1)
        )
        false -> listOf(
            Position(row, position.column - 1),
            Position(row, position.column + 1)
        )
    }.filter { it.isCorrect() }
}