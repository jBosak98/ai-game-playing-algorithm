package algorithm.ai

import algorithm.evaluateGame
import algorithm.makeMove
import lib.Team.Team
import lib.game.Game
import lib.game.Move
import lib.game.getListOfMoves
import kotlin.math.max
import kotlin.math.min


private fun minOrMaxFunction(isMax: Boolean): (Int, Int) -> Int {
    return fun(score: Int, secondScore): Int =
        when (isMax) {
            true -> max(score, secondScore)
            else -> min(score, secondScore)
        }
}


private fun winnerScore(winner: Team?, player: Team, depth: Int): Int =
    when (winner) {
        player -> Int.MAX_VALUE / depth
        else -> Int.MIN_VALUE / depth
    }
fun gameAlgorithm(isAlphaBeta: Boolean): (Game, Team, Int) -> Move {
    val initialAlpha = Int.MIN_VALUE
    val beta = Int.MAX_VALUE
    return fun(game: Game, team: Team, maxDepth: Int): Move {
        val gameAlgorithm = gameAlgorithmInit(maxDepth, team)

        val getOptimalMoves = fun(acc: List<Pair<Move, Int>>, move: Move): List<Pair<Move, Int>> {
            val alpha =
                if(isAlphaBeta) gameAlgorithm(game, move, 1, initialAlpha, beta)
                else gameAlgorithm(game, move, 1, null, null)//in minMax algorithm, it is score

            return when {
                acc.isEmpty() -> listOf(Pair(move, alpha))
                acc.first().second < alpha -> listOf(Pair(move, alpha))
                acc.first().second == alpha -> acc + Pair(move, alpha)
                acc.first().second > alpha -> acc
                else -> listOf(Pair(move, alpha))
            }
        }
        return game
            .getListOfMoves()
            .fold(emptyList(), getOptimalMoves)
            .shuffled()//moves in this collection have the same (which is the best) score
            .first()// so I'm taking the random move with score
            .first //means that I'm taking the Move, not the score nor move with score

    }
}

private fun gameAlgorithmInit(maxDepth: Int, team: Team): (Game, Move, Int, Int?, Int?) -> Int {
    fun gameAlgorithmGenerator(game: Game, depth: Int): (Move, Int?, Int?) -> Int =
        fun(move: Move, alpha: Int?, beta: Int?): Int {
            val gameAfterMove = makeMove(game, move)

            if (gameAfterMove.isFinished) return winnerScore(gameAfterMove.winner, gameAfterMove.nextMove, depth)
            if (depth >= maxDepth) return evaluateGame(gameAfterMove, gameAfterMove.nextMove)

            val isMax = gameAfterMove.nextMove == team
            val isAlphaBeta = alpha != null && beta != null
            val gameAlgorithmFun = gameAlgorithmGenerator(gameAfterMove, depth + 1)
            val evaluateNextMoveFun = evaluateNextMoveGenerator(isMax, gameAlgorithmFun)
            val bestScoreInit: Int = getBestScoreInit(isAlphaBeta, isMax, alpha, beta)
            val initParameter = getInitParameter(isAlphaBeta, bestScoreInit, alpha, beta)

            val bestScore = gameAfterMove
                .getListOfMoves()
                .fold(initParameter) { acc, nextMove: Move ->
                    val result = evaluateNextMoveFun(acc, nextMove)
                    if (result is GameAlgorithmInput.AlphaBetaInput && result.alpha >= result.beta)//alpha beta pruning
                        return result.score
                    result
                }

            return when (bestScore) {
                is GameAlgorithmInput.AlphaBetaInput -> bestScore.score
                is GameAlgorithmInput.MinMaxInput -> bestScore.score
            }
        }
    return fun(game: Game, move: Move, depth: Int, alpha: Int?, beta: Int?) =
        gameAlgorithmGenerator(game, depth)(move, alpha, beta)
}

private fun evaluateNextMoveGenerator(isMax: Boolean, minMax: (Move, Int?, Int?) -> Int): (GameAlgorithmInput, Move) -> GameAlgorithmInput {
    val getTheBest = minOrMaxFunction(isMax)
    return fun(params: GameAlgorithmInput, nextMove: Move): GameAlgorithmInput {
        return when (params) {
            is GameAlgorithmInput.AlphaBetaInput -> {
                val result = getTheBest(minMax(nextMove, params.alpha, params.beta), params.score)
                val updatedAlpha = if (isMax) result else params.alpha
                val updatedBeta = if (!isMax) result else params.beta
                GameAlgorithmInput.AlphaBetaInput(updatedAlpha, updatedBeta, result)
            }
            is GameAlgorithmInput.MinMaxInput -> {
                val score = getTheBest(params.score, minMax(nextMove, null, null))
                GameAlgorithmInput.MinMaxInput(score)
            }
        }

    }
}

private fun getInitParameter(isAlphaBeta:Boolean,bestScore:Int, alpha:Int? = null,beta:Int? = null) =
    if (isAlphaBeta) GameAlgorithmInput.AlphaBetaInput(alpha!!, beta!!, bestScore)
    else GameAlgorithmInput.MinMaxInput(bestScore)


private fun getBestScoreInit(isAlphaBeta:Boolean, isMax:Boolean, alpha:Int? = null, beta:Int? = null): Int {
    return  when (isAlphaBeta) {
        true -> if (isMax) alpha else beta
        else -> if (isMax) Int.MIN_VALUE else Int.MAX_VALUE
    } as Int
}