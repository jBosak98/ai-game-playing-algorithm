package algorithm.ai

import algorithm.makeMove
import algorithm.shouldAIMakeMove
import lib.game.*
import lib.gameConfig.AlgorithmType
import lib.list.maybeFirst
import lib.pawns.getPawns

fun makeMoveByAI(game: Game): Game {
    val possibleMoves = game.pawns.getPawns(game.nextMove)
        .filter { game.possibleMoves.keys.contains(it.key) }
        .map { it.value to game.possibleMoves.getValue(it.key) }
        .filter { it.second.isNotEmpty() }
    if (possibleMoves.isEmpty() && possibleMoves.all { it.second.isEmpty() }) {
        val isFinished = isFinished(game.pawns, game.possibleMoves)
        return Game(
            pawns = game.pawns,
            nextMove = game.nextMove,
            isFinished = isFinished,
            possibleMoves = game.possibleMoves,
            winner = if (isFinished) getWinner(game.pawns, game.possibleMoves) else null,
            config = game.config
        )
    }
    val firstMove = possibleMoves
        .toList()
        .filter { it.second.isNotEmpty() }
        .shuffled()
        .maybeFirst()!!

    val aiConfig = game.config?.players?.find { it.team == game.nextMove }

    if (shouldAIMakeMove(game) && firstMove.second.isNotEmpty() && aiConfig != null && aiConfig.depth != null) {
        val possibleMove = firstMove.second.shuffled().first()

        val algorithm = when (aiConfig.algorithmType) {
            AlgorithmType.MIN_MAX -> gameAlgorithm(isAlphaBeta = false)
            AlgorithmType.ALPHA_BETA -> gameAlgorithm(isAlphaBeta = true)
            else -> { _, _, _ -> Move(firstMove.first, possibleMove.destination) } //if error, then random
        }

        val move = algorithm(game, game.nextMove, aiConfig.depth)
        return makeMove(game, move)
    }
    return game

}