package algorithm.ai

import algorithm.evaluateGame
import algorithm.makeMove
import lib.Team.Team
import lib.Team.opposite
import lib.game.Game
import lib.game.Move
import lib.pawns.getPawns
import kotlin.math.max
import kotlin.math.min


private fun minOrMaxFunction(isMax: Boolean): (Int, Int) -> Int =
    fun(score: Int, secondScore): Int =
        when (isMax) {
            true -> max(score, secondScore)
            else -> min(score, secondScore)
        }

private fun winnerScore(winner: Team?, player: Team, depth: Int): Int =
    when (winner) {
        player -> Int.MAX_VALUE / depth
        else -> Int.MIN_VALUE / depth
    }

fun minmax(game: Game, team: Team, maxDepth: Int): Move {
    val moves = getMoves(game)
    val bestMoves = moves.fold(listOf<Pair<Move, Int>>()) { acc, move ->
        val score = minmax(game, move, 1, team, maxDepth)
        when {
            acc.isEmpty() -> listOf(Pair(move, score))
            acc.first().second < score -> listOf(Pair(move, score))
            acc.first().second == score -> acc + Pair(move, score)
            acc.first().second > score -> acc
            else -> listOf(Pair(move, score))
        }

    }
    return bestMoves.first().first
}

fun minmax(game: Game, move: Move, depth: Int, team: Team, maxDepth: Int): Int {
    val gameAfterMove = makeMove(game, move)

    if (gameAfterMove.isFinished)
        return winnerScore(gameAfterMove.winner, gameAfterMove.nextMove, depth)
    if (depth >= maxDepth)
        return evaluateGame(gameAfterMove, gameAfterMove.nextMove)

    val isMax = gameAfterMove.nextMove == team
    val bestScore = if (isMax) Int.MIN_VALUE else Int.MAX_VALUE

    return getMoves(gameAfterMove)
        .map { minmax(gameAfterMove, it, depth + 1, team, maxDepth) }
        .fold(bestScore, minOrMaxFunction(isMax))
}


private fun getMoves(game: Game) =
    game.pawns.getPawns(game.nextMove)
        .filter {
            game.possibleMoves.keys.contains(it.key)
        }
        .map {
            it.value to game.possibleMoves.getValue(it.key)
        }.toList()
        .flatMap { it.second.map { move -> Pair(it.first, move) } }
        .map {
            Move(it.first, it.second.destination)
        }

