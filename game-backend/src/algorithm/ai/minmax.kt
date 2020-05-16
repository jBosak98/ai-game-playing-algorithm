package algorithm.ai

import algorithm.evaluateGame
import algorithm.makeMove
import lib.Team.Team
import lib.game.Game
import lib.game.Move
import lib.game.getListOfMoves
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
    val minMaxAlgorithm = minMaxInit(maxDepth, team)
    val getOptimalMoves = fun(acc:List<Pair<Move, Int>>, move:Move): List<Pair<Move, Int>> {
        val score = minMaxAlgorithm(game, move, 1)
        return when {
            acc.isEmpty() -> listOf(Pair(move, score))
            acc.first().second < score -> listOf(Pair(move, score))
            acc.first().second == score -> acc + Pair(move, score)
            acc.first().second > score -> acc
            else -> listOf(Pair(move, score))
        }
    }
    return game
        .getListOfMoves()
        .fold(emptyList(), getOptimalMoves)
        .shuffled()//moves in this collection have the same (which is the best) score
        .first()// so I'm taking the random move with score
        .first //means that I'm taking the Move, not the score nor move with score

}

private fun minMaxInit(maxDepth: Int, team:Team): (Game, Move, Int) -> Int {
    fun minMaxGenerator(game:Game, depth:Int): (Move) -> Int =
        fun (move:Move): Int {
            val gameAfterMove = makeMove(game, move)
            val isMax = gameAfterMove.nextMove == team
            val bestScore = if (isMax) Int.MIN_VALUE else Int.MAX_VALUE
            val minMaxFunction = minMaxGenerator(gameAfterMove, depth + 1)
            return when {
                gameAfterMove.isFinished -> winnerScore(gameAfterMove.winner, gameAfterMove.nextMove, depth)
                depth >= maxDepth -> evaluateGame(gameAfterMove, gameAfterMove.nextMove)
                else -> gameAfterMove
                    .getListOfMoves()
                    .map(minMaxFunction)
                    .fold(bestScore, minOrMaxFunction(isMax))
            }
        }
    return fun(game: Game, move: Move, depth: Int) = minMaxGenerator(game, depth)(move)
}




