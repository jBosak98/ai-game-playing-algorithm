package algorithm.ai

import algorithm.makeMove
import algorithm.shouldAIMakeMove
import kotlinx.coroutines.delay
import lib.Team.opposite
import lib.game.*
import lib.list.maybeFirst
import lib.pawns.getPawns

fun makeMoveByAI(game:Game):Game {
    val possibleMoves = game.pawns.getPawns(game.nextMove)
        .filter{
            game.possibleMoves.keys.contains(it.key)
        }
        .map {
        it.value to game.possibleMoves.getValue(it.key)
    }
    if(possibleMoves.isEmpty()){
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
    val firstMove = possibleMoves.toList().filter { it.second.isNotEmpty() }.maybeFirst()!!
    if(shouldAIMakeMove(game) && firstMove.second.isNotEmpty()){
        val possibleMove = firstMove.second.first()
        val move = Move(firstMove.first, possibleMove.destination)
        return makeMove(game, move)
    }
    return game

}