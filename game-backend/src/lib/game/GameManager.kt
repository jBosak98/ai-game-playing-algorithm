package lib.game

import lib.pawns.toPawnView

fun Game.toGameView(): GameView {
    val pawnsView = this.pawns.map {
        it
            .value
            .toPawnView(this.possibleMoves[it.key] ?: emptyList())

    }
    return GameView(
        pawns = pawnsView,
        nextMove = this.nextMove,
        isFinished = this.isFinished
    )

}