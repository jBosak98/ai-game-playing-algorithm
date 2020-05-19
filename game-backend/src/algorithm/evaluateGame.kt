package algorithm

import lib.Team.Team
import lib.Team.firstRow
import lib.Team.opposite
import lib.game.Game
import lib.game.PossibleMoves
import lib.pawns.Pawns
import lib.pawns.getPawns
import lib.position.Position

fun evaluateGame(game:Game, team:Team):Int {
    val pawns = game.pawns.getPawns(team)
    val pointsForNumberOfPawns = getPointsForNumberOfPawns(pawns)

    val differenceBetweenNumberOfPawns = getDifferenceBetweenNumberOfPawns(game.pawns, team)

    return when {
        pointsForNumberOfPawns == 0 -> Int.MIN_VALUE
        getPointsForNumberOfPawns(game.pawns.getPawns(team.opposite())) == 0 -> Int.MAX_VALUE
        else -> differenceBetweenNumberOfPawns
    }
}


fun evaluateGame2(game:Game, team:Team):Int =
    game.pawns.getPawns(team).count() - game.pawns.getPawns(team.opposite()).count()


private fun getDifferenceBetweenNumberOfPawns(allPawns:Pawns, team:Team) =
    getPointsForNumberOfPawns(allPawns.getPawns(team)) -
            getPointsForNumberOfPawns(allPawns.getPawns(team.opposite()))

private fun getPossibleMovesFromGame(pawns:Pawns): (Map.Entry<Position, PossibleMoves>) -> List<Position> {
    return fun(possibleMove:Map.Entry<Position, PossibleMoves>): List<Position> {
        val (key,_) = possibleMove
        return if(pawns[key] == null) emptyList()
        else pawns.getValue(key).possibleMoves
    }
}

private fun getPointsForNumberOfPawns(pawns:Pawns) =
    pawns.map { if(it.value.isKing) 2 else 1 }.sum()