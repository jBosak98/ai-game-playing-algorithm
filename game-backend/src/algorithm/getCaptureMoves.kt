package algorithm

import lib.Team.Team
import lib.Team.opposite
import lib.game.PossibleMove
import lib.list.maybeFirst
import lib.pawns.Pawn
import lib.pawns.Pawns
import lib.pawns.fieldOccupiedByTeam
import lib.pawns.isFieldOccupied
import lib.position.Position
import lib.position.isCorrect
import lib.tools.getTheBestCaptureSet


fun getCaptureMoves(
    position: Position,
    team: Team,
    pawns: Pawns,
    capturedPawns: Int
): List<PossibleMove> {
    val pawn = pawns.getValue(position)
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
    val getPairOfMoveWithKills = fun(destination: Position): PossibleMove {
        val movedPawn = pawns[position] ?: return PossibleMove(position, capturedPawns, pawns)
        val newPawn = Pawn(
            destination.row,
            destination.column,
            movedPawn.team,
            movedPawn.isKing,
            emptyList()
        )
        val pawnsMoved = pawns.toMutableMap().also { it[destination] = newPawn }
        val filteredPawns = filterCapturedPawn(position, destination, pawnsMoved)
        return PossibleMove(destination, capturedPawns + 1, filteredPawns)
    }

    val getMultiCaptureMoves = fun(singleCapture: PossibleMove): List<PossibleMove> {
        val (move, kills, pawnsAfterCapture) = singleCapture
        val filteredPawns = filterCapturedPawn(position, move, pawnsAfterCapture)
        return getCaptureMoves(move, team, filteredPawns, kills)
    }

    val getTheLongestCaptures =
        fun(acc: List<PossibleMove>, item: PossibleMove): List<PossibleMove> {
            val itemKills = item.captures
            val accKills = acc.maybeFirst()?.captures ?: -1
            return getTheBestCaptureSet(accKills, itemKills, item, acc)
        }

    val possibleMoves =
        getDiagonalMovesPositions(position, team, pawn.isKing)
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

