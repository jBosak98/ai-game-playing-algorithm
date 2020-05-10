package algorithm

import lib.pawns.*
import lib.position.Position
import lib.Team.Team
import lib.list.maybeFirst
import lib.tools.getTheBestCaptureSet

fun getPossibleMoves(pawns: Pawns): Map<Position, List<Position>> {
    val blackMoves = getPossibleMovesOfTeam(pawns, Team.BLACK)
    val whiteMoves = getPossibleMovesOfTeam(pawns, Team.WHITE)
    return (blackMoves + whiteMoves)
}

fun getPossibleMovesOfTeam(pawns: Pawns, team: Team): Map<Position, List<Position>> {
    val shortMoves = getShortMoves(pawns, team)
    val longMoves = getLongMoves(pawns, team)

    return if (longMoves.values.isEmpty()) shortMoves
    else longMoves

}

private fun getShortMoves(pawns: Pawns, team: Team): Map<Position, List<Position>> {
    val getMoves = fun(pawnWithPosition: Map.Entry<Position, Pawn>): Pair<Position, List<Position>> {
        val (pawnPosition, pawn) = pawnWithPosition
        val moves = getDiagonalMovesPositions(pawn)

        return pawnPosition to moves.filter { position ->
            !pawns.isFieldOccupied(position)
        }
    }
    return pawns
        .getPawns(team)
        .map(getMoves)
        .toMap()
}

private fun getLongMoves(pawns: Pawns, team: Team): Map<Position, List<Position>> {
    val getLongMoves = fun(pawnWithPosition: Map.Entry<Position, Pawn>): Pair<Position, List<Pair<Position, Int>>> {
        val (pawnPosition, pawn) = pawnWithPosition
        val moves = getCaptureMoves(pawnPosition, pawn.team, pawns, 1)
        return pawnPosition to moves.filter { (position, _) ->
            !pawns.isFieldOccupied(position) || position == pawnPosition
        }
    }
    val getTheLongestCaptures = fun(acc:List<Pair<Position, List<Pair<Position, Int>>>>, item:Pair<Position, List<Pair<Position, Int>>>): List<Pair<Position, List<Pair<Position, Int>>>> {
        val accKills = acc.maybeFirst()?.second?.maybeFirst()?.second ?: -1
        val itemKills = item.second.maybeFirst()?.second ?: -1
        return getTheBestCaptureSet(accKills, itemKills, item, acc)
    }
    val convertToDestinationPositions = fun(capture:Pair<Position, List<Pair<Position, Int>>>): Pair<Position, List<Position>> {
        val (initialPosition, movesWithKills) = capture
        return initialPosition to movesWithKills.map { (destination, _) -> destination }
    }

    return pawns
        .getPawns(team)
        .map(getLongMoves)
        .fold(listOf(), getTheLongestCaptures)
        .map(convertToDestinationPositions)
        .toMap()
}


