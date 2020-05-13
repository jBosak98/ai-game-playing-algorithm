package algorithm

import lib.Team.Team
import lib.game.PossibleMove
import lib.game.PossibleMoves
import lib.list.maybeFirst
import lib.pawns.*
import lib.position.InitPosition
import lib.position.Position
import lib.tools.getTheBestCaptureSet


fun getPossibleMoves(pawns: Pawns): Map<InitPosition, PossibleMoves> {
    val blackMoves = getPossibleMovesOfTeam(pawns, Team.BLACK)
    val whiteMoves = getPossibleMovesOfTeam(pawns, Team.WHITE)
    return (blackMoves + whiteMoves)
}

fun getPossibleMovesOfTeam(pawns: Pawns, team: Team): Map<InitPosition, PossibleMoves> {
    val shortMoves = getShortMoves(pawns, team)
    val longMoves = getLongMoves(pawns, team)

    val isLongMovesEmpty = longMoves.values.all { it.isEmpty() }
    return if (isLongMovesEmpty) shortMoves
    else longMoves

}
private fun makeShortMove(pawns:Pawns,pawn:Pawn): (Position) -> PossibleMove {
    return fun(destination:Position):PossibleMove{
        val pawnsAfterMove = pawns.filter { it.key != pawn.toPosition() }.toMutableMap()
        val newPawn = Pawn(destination.row, destination.column, pawn.team, pawn.isKing, emptyList())
        pawnsAfterMove[destination] = newPawn
        return PossibleMove(destination,0, pawnsAfterMove)
    }
}

private fun getShortMoves(pawns: Pawns, team: Team): Map<InitPosition, PossibleMoves> {
    val getMoves = fun(pawnWithPosition: Map.Entry<Position, Pawn>): Pair<Position, List<PossibleMove>> {
        val (pawnPosition, pawn) = pawnWithPosition
        val shortMove = makeShortMove(pawns, pawn)

        val moves = getDiagonalMovesPositions(pawn).map(shortMove)

        return pawnPosition to moves.filter { (position,_,_) ->
            !pawns.isFieldOccupied(position)
        }
    }
    return pawns
        .getPawns(team)
        .map(getMoves)
        .toMap()
}

private fun getLongMoves(pawns: Pawns, team: Team): Map<InitPosition, PossibleMoves> {
    val getLongMoves = fun(pawnWithPosition: Map.Entry<Position, Pawn>): Pair<Position, PossibleMoves> {
        val (pawnPosition, pawn) = pawnWithPosition
        val moves = getCaptureMoves(pawnPosition, pawn.team, pawns, 1)
        return pawnPosition to moves.filter { (position, _) ->
            !pawns.isFieldOccupied(position) || position == pawnPosition
        }
    }
    val getTheLongestCaptures = fun(acc:List<Pair<InitPosition, PossibleMoves>>, item:Pair<InitPosition, PossibleMoves>): List<Pair<InitPosition, PossibleMoves>> {
        val accKills = acc.maybeFirst()?.second?.maybeFirst()?.captures ?: -1
        val itemKills = item.second.maybeFirst()?.captures ?: -1
        return getTheBestCaptureSet(accKills, itemKills, item, acc)
    }
    val convertToDestinationPositions = fun(capture:Pair<InitPosition, PossibleMoves>): Pair<InitPosition, PossibleMoves> {
        val (initialPosition, movesWithKills) = capture
        return initialPosition to movesWithKills
    }

    return pawns
        .getPawns(team)
        .map(getLongMoves)
        .fold(listOf(), getTheLongestCaptures)
        .map(convertToDestinationPositions)
        .toMap()
}


