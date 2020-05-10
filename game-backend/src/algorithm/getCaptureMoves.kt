package algorithm

import lib.Team.Team
import lib.Team.opposite
import lib.list.maybeFirst
import lib.position.isCorrect
import lib.pawns.Pawn
import lib.pawns.Pawns
import lib.pawns.fieldOccupiedByTeam
import lib.pawns.isFieldOccupied
import lib.position.Position
import lib.tools.getTheBestCaptureSet

fun getCaptureMoves(
    position: Position,
    team: Team,
    pawns: Map<Position, Pawn>,
    capturedPawns: Int
): List<Pair<Position, Int>> {
    val filterFieldsOccupiedByTheSameTeam = fun(movePosition: Position): Boolean {
        return pawns.fieldOccupiedByTeam(movePosition) === team.opposite()
    }
    val getDestinationPosition = fun(capturedPawnPosition: Position): Position {
        return Position(
            2 * capturedPawnPosition.row - position.row,
            2 * capturedPawnPosition.column - position.column
        )
    }
    val filterOccupiedFields = fun(position: Position): Boolean {
        return !pawns.isFieldOccupied(position) && position.isCorrect()
    }
    val getPairOfMoveWithKills = fun(destination: Position): Pair<Position, Int> {
        return Pair(destination, capturedPawns + 1)
    }

    val getMultiCaptureMoves = fun(singleCapture: Pair<Position, Int>): List<Pair<Position, Int>> {
        val (move, kills) = singleCapture
        val filteredPawns = filterCapturedPawn(position, move, pawns)
        return getCaptureMoves(move, team, filteredPawns, kills)
    }

    val getTheLongestCaptures =
        fun(acc: List<Pair<Position, Int>>, item: Pair<Position, Int>): List<Pair<Position, Int>> {
            val itemKills = item.second
            val accKills = acc.maybeFirst()?.second ?: -1
            return getTheBestCaptureSet(accKills, itemKills, item, acc)
        }

    val possibleMoves =
        getDiagonalMovesPositions(position, team, true)
            .filter(filterFieldsOccupiedByTheSameTeam)
            .map(getDestinationPosition)
            .filter(filterOccupiedFields)
            .map(getPairOfMoveWithKills)

    val multiCaptureMoves = possibleMoves
        .flatMap(getMultiCaptureMoves)

    return (possibleMoves + multiCaptureMoves)
        .fold(emptyList(), getTheLongestCaptures)
}

private fun filterCapturedPawn(position: Position, destination: Position, pawns: Pawns) =
    pawns.filterKeys {
        val capturedPawnPosition = Position(
            (destination.row + position.row) / 2,
            (destination.column + position.column) / 2
        )
        it != capturedPawnPosition && it != position
    }

