package lib.game

import algorithm.getPossibleMoves
import algorithm.getPossibleMovesOfTeam
import lib.pawns.toPawn
import lib.pawns.toPawnView
import lib.pawns.toPosition
import lib.position.Position
import lib.position.isCorrect

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

fun GameView.toGame(): Game{
    val pawns = this.pawns.map { Position(it.row,it.column) to it.toPawn() }.toMap()
    return Game(
        pawns = pawns,
        nextMove = this.nextMove,
        isFinished = this.isFinished,
        possibleMoves = getPossibleMoves(pawns)
    )
}

fun GameWithMove.toGame():Game{
    val pawns = this.board.pawns.map { Position(it.row,it.column) to it.toPawn() }.toMap()
    return Game(
        pawns = pawns,
        nextMove = this.board.nextMove,
        isFinished = this.board.isFinished,
        possibleMoves = getPossibleMoves(pawns)
    )

}
fun Move.isValid(game:Game): Boolean {
    val movedPawn = this.pawn
    val isPawnPositionCorrect = movedPawn.toPosition().isCorrect()
    val isDestinationCorrect = this.destination.isCorrect()
    val isPawnInBoard = game.pawns.any {
        it.key.row == movedPawn.row &&
        it.key.column == movedPawn.column &&
        it.value.isKing == movedPawn.isKing &&
        it.value.team == movedPawn.team
    }
    val isMoveAsPossibleMove = game
        .possibleMoves[movedPawn.toPosition()]
        ?.contains(destination) ?: false
    val isMoveWithCorrectTeam = game.nextMove == this.pawn.team

    return isPawnPositionCorrect &&
            isDestinationCorrect &&
            isPawnInBoard &&
            isMoveAsPossibleMove &&
            isMoveWithCorrectTeam

}
fun GameWithMove.isValid(): Boolean {
    val arePawnsPositionCorrect = board.pawns.all { Position(it.row, it.column).isCorrect() }
    val isMovePositionCorrect = this.move.destination.isCorrect()
    return arePawnsPositionCorrect && isMovePositionCorrect
}