package algorithm

import lib.Team.opposite
import lib.game.*
import lib.pawns.makeKing
import lib.pawns.shouldChangeToKing
import lib.pawns.toPosition


fun makeMove(gameWithMove: GameWithMove): GameView {
    val game = gameWithMove.toGame()
    val move = gameWithMove.move
    if (gameWithMove.isValid() && move.isValid(game)) {
        val newGame = makeMove(game, move)
        return newGame.toGameView()
    }
    return game.toGameView()
}

fun makeMove(game: Game, move: Move): Game {
    val pawns = game
        .possibleMoves[move.pawn.toPosition()]
        ?.find { it.destination == move.destination }
        ?.pawns?.map {
            val shouldBeKing = it.value.shouldChangeToKing()
            it.key to
                    if(shouldBeKing) it.value.makeKing()
                    else it.value
        }?.toMap() ?: return game

    return Game(
        pawns = pawns,
        nextMove = game.nextMove.opposite(),
        isFinished = false,
        possibleMoves = getPossibleMoves(pawns)
    )
}