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
    val numberOfAllPawns = 12
    val pawns = game.pawns.getPawns(team)
    val pointsForNumberOfPawns = getPointsForNumberOfPawns(pawns)
    val pointsForEnemiesPawns = (numberOfAllPawns - getPointsForNumberOfPawns(game.pawns.getPawns(team.opposite()))) / 2

    val differenceBetweenNumberOfPawns = getDifferenceBetweenNumberOfPawns(game.pawns, team) * 3
    val pointsForFirstRow = pawns.filter { it.value.team.firstRow() == it.value.row }.count()
    val possibleMoves = game.possibleMoves.flatMap(getPossibleMovesFromGame(pawns)).count()

    val sum = differenceBetweenNumberOfPawns
    //pointsForNumberOfPawns// +
//                pointsForEnemiesPawns +
//        differenceBetweenNumberOfPawns +
//                pointsForFirstRow

    return when {
        pointsForNumberOfPawns == 0// || possibleMoves == 0
        -> Int.MIN_VALUE
//        possibleMoves in 1..2 -> sum * 3 / 4
        else -> sum
    }

}

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