package algorithm.ai

import algorithm.makeMove
import algorithm.shouldAIMakeMove
import lib.game.*
import lib.gameConfig.AlgorithmType
import lib.list.maybeFirst
import lib.pawns.getPawns

fun makeMoveByAI(game:Game):Game {
    val possibleMoves = game.pawns.getPawns(game.nextMove)
        .filter{
            game.possibleMoves.keys.contains(it.key)
        }
        .map {
        it.value to game.possibleMoves.getValue(it.key)
    }.filter { it.second.isNotEmpty() }
    if(possibleMoves.isEmpty() && possibleMoves.all { it.second.isEmpty() }){
        val isFinished = isFinished(game.pawns, game.possibleMoves)
        return Game(
            pawns = game.pawns,
            nextMove = game.nextMove,
            isFinished = isFinished,
            possibleMoves = game.possibleMoves,
            winner = if(isFinished) getWinner(game.pawns, game.possibleMoves) else null,
            config = game.config
        )
    }
    val firstMove = possibleMoves
        .toList()
        .filter { it.second.isNotEmpty() }
        .shuffled()
        .maybeFirst()!!

    val aiConfig = game.config?.players?.find { it.team == game.nextMove }

    if(shouldAIMakeMove(game) && firstMove.second.isNotEmpty() && aiConfig != null && aiConfig.depth != null){
        val possibleMove = firstMove.second.shuffled().first()
        val move = if(aiConfig.algorithmType == AlgorithmType.MIN_MAX)
            minmax(game,game.nextMove,aiConfig.depth)
        else
            Move(firstMove.first, possibleMove.destination)

        return makeMove(game, move)
    }
    return game

}