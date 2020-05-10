package algorithm

import lib.pawns.*
import model.Position
import lib.Team.Team
import lib.Team.opposite
import lib.list.maybeFirst

fun getPossibleMoves(pawns: Pawns): Map<Position, List<Position>> {
    val blackMoves = getPossibleMovesOfTeam(pawns, Team.BLACK)
    val whiteMoves = getPossibleMovesOfTeam(pawns, Team.WHITE)
    return (blackMoves + whiteMoves) as Map<Position, List<Position>>
}

fun getPossibleMovesOfTeam(pawns: Pawns, team: Team): Map<Position, List<Position>> {

    val shortMoves = pawns
        .getPawns(team)
        .map {
            val moves = getDiagonalMovesPositions(it.value)

            it.key to moves.filter { position ->
                !pawns.isFieldOccupied(position)
            }
        }.toMap()
    val longMoves = pawns
        .getPawns(team)
        .map {
            val moves = getCaptureMoves(it.value, pawns)
            it.key to moves.filter { (position, _) ->
                !pawns.isFieldOccupied(position) || position == it.key
            }
        }
        .fold(listOf<Pair<Position, List<Pair<Position, Int>>>>()) { acc, item: Pair<Position, List<Pair<Position, Int>>> ->
            val accKills = acc.maybeFirst()?.second?.maybeFirst()?.second ?: -1
            val itemKills = item.second.maybeFirst()?.second ?: -1
            when {
                accKills < itemKills -> listOf(item)
                accKills == itemKills -> acc + item
                else -> acc
            }

        }.map { (initialPosition, movesWithKills) ->
            initialPosition to movesWithKills.map { (destination, _) -> destination }
        }
        .toMap()

    return if (longMoves.values.isEmpty()) shortMoves else longMoves
}

fun getCaptureMoves(pawn: Pawn, pawns: Pawns): List<Pair<Position, Int>> {
    val filterFieldsOccupiedByTheSameTeam = fun(movePosition: Position): Boolean {
        return pawns.fieldOccupiedByTeam(movePosition) === pawn.team.opposite()
    }

    val getDestinationPosition = fun(capturedPawnPosition: Position): Position {
        return Position(2 * capturedPawnPosition.row - pawn.row, 2 * capturedPawnPosition.column - pawn.column)
    }

    val filterOccupiedFields = fun(position: Position): Boolean {
        return !pawns.isFieldOccupied(position) && isPositionCorrect(position)
    }

    val getMultiCaptureMoves = fun(singleCapture: Position): List<Pair<Position, Int>> {
        val filteredPawns = filterCapturedPawn(pawn.getPosition(), singleCapture, pawns)
        return getCaptureMoves(singleCapture, pawn.team, filteredPawns, 1)
    }
    val getTheLongestCaptures = fun(acc: List<Pair<Position, Int>>, item: Pair<Position, Int>): List<Pair<Position, Int>> {
        val itemKills = item.second
        val accKills = if (acc.isEmpty()) -1 else acc.first().second
        return when {
            accKills < itemKills -> listOf(item)
            accKills == itemKills -> acc + item
            else -> acc
        }
    }
    return getDiagonalMovesPositions(pawn)
        .filter(filterFieldsOccupiedByTheSameTeam)
        .map (getDestinationPosition)
        .filter (filterOccupiedFields)
        .flatMap(getMultiCaptureMoves)
        .fold(listOf(), getTheLongestCaptures)
}

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
        return Position(2 * capturedPawnPosition.row - position.row, 2 * capturedPawnPosition.column - position.column)
    }
    val filterOccupiedFields = fun(position: Position): Boolean {
        return !pawns.isFieldOccupied(position) && isPositionCorrect(position)
    }
    val getPairOfMoveWithKills = fun(destination: Position): Pair<Position, Int> {
        return Pair(destination, capturedPawns + 1)
    }

    val getMultiCaptureMoves = fun(singleCapture: Pair<Position, Int>): List<Pair<Position, Int>> {
        val (move, kills) = singleCapture
        val filteredPawns = filterCapturedPawn(position, move, pawns)
        return getCaptureMoves(move, team, filteredPawns, kills)
    }

    val possibleMoves =
        getDiagonalMovesPositions(position, team, true)
            .filter(filterFieldsOccupiedByTheSameTeam)
            .map(getDestinationPosition)
            .filter(filterOccupiedFields)
            .map(getPairOfMoveWithKills)

    val multiCaptureMoves = possibleMoves
        .flatMap(getMultiCaptureMoves)

    return possibleMoves + multiCaptureMoves
}


fun filterCapturedPawn(position: Position, destination: Position, pawns: Pawns) =
    pawns.filterKeys {
        val capturedPawnPosition = Position(
            (destination.row + position.row) / 2,
            (destination.column + position.column) / 2
        )
        it != capturedPawnPosition && it != position
    }


fun isPositionCorrect(position: Position): Boolean =
    position.column in 0..7 && position.row in 0..7



