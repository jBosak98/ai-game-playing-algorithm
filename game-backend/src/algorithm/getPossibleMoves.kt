package algorithm

import lib.pawns.*
import model.Position
import lib.Team.Team
import lib.Team.opposite

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
//            moves
//            it.key to
            it.key to moves.filter { position ->
                !pawns.isFieldOccupied(position) || position == it.key
            }
        }.toMap()



    return if (longMoves.values.isEmpty()) shortMoves else longMoves
}

fun getCaptureMoves(pawn: Pawn, pawns: Pawns): List<Position> {
    return getDiagonalMovesPositions(pawn)
        .filter { pawns.fieldOccupiedByTeam(it) === pawn.team.opposite() }
        .map { Position(2 * it.row - pawn.row, 2 * it.column - pawn.column) }
        .filter { position -> !pawns.isFieldOccupied(position) }
        .flatMap { movePosition ->
            val pawnsWithoutCaptured =
                filterCapturedPawn(pawn.getPosition(), movePosition, pawns)
            getCaptureMoves(movePosition, pawn.team, pawnsWithoutCaptured, 1)
        }.fold(emptyList()) { acc: List<Pair<Position, Int>>, item: Pair<Position, Int> ->
            val itemKills = item.second
            val accKills = if (acc.isEmpty()) -1 else acc.first().second
            when {
                accKills < itemKills -> listOf(item)
                accKills == itemKills -> acc + item
                else -> acc
            }
        }.map {
            it.first
        }
}

fun getCaptureMoves(
    position: Position,
    team: Team,
    pawns: Map<Position, Pawn>,
    capturedPawns: Int
): List<Pair<Position, Int>> {
    val possibleMoves = getDiagonalMovesPositions(position, team, true)
        .filter { pawns.fieldOccupiedByTeam(it) === team.opposite() }
        .map { Position(2 * it.row - position.row, 2 * it.column - position.column) }
        .filter { !pawns.isFieldOccupied(it) }
        .map { Pair(it, capturedPawns + 1) }
    if (possibleMoves.isEmpty()) return possibleMoves
    val morePossibleMoves = possibleMoves.flatMap { (move: Position, capturedPawns: Int) ->
        val filteredPawns = filterCapturedPawn(position, move, pawns)
        getCaptureMoves(move, team, filteredPawns, capturedPawns)
    }.toList()
    return possibleMoves + morePossibleMoves
}

fun filterCapturedPawn(position: Position, destination: Position, pawns: Pawns) =
    pawns.filterKeys {
        val capturedPawnPosition = Position(
            (destination.row + position.row) / 2,
            (destination.column + position.column) / 2
        )
        it != capturedPawnPosition && it != position
    }

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
    }
}

fun isPositionCorrect(position: Position): Boolean =
    position.column in 0..7 && position.row in 0..7



