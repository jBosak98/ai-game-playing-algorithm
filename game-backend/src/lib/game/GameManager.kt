package lib.game

import algorithm.getPossibleMoves
import lib.pawns.PawnView
import lib.pawns.toPawnView

fun Game.toGameView(): GameView {
    val pawnsView = this.pawns.map {
        it.value.toPawnView(this.possibleMoves[it.key]!!)

    }
    return GameView(
        pawns = pawnsView,
        nextMove = this.nextMove,
        isFinished = this.isFinished
    )

}