package algorithm

import lib.Team.opposite
import lib.game.*
import lib.pawns.Pawn
import lib.pawns.toPosition


fun makeMove(gameWithMove: GameWithMove): GameView {
    val game = gameWithMove.toGame()
    val move = gameWithMove.move
    if(gameWithMove.isValid() && move.isValid(game)){
        val newPawn = Pawn(
            row = move.destination.row,
            column = move.destination.column,
            team = move.pawn.team,
            isKing = move.pawn.isKing
        )
        val newPawns = game
            .pawns
            .filterKeys { it != move.pawn.toPosition() }
            .toMutableMap()
        newPawns[newPawn.toPosition()] = newPawn
        val newGame = Game(
            pawns = newPawns,
            nextMove = game.nextMove.opposite(),
            isFinished = false,
            possibleMoves = getPossibleMoves(newPawns)
        )
        return newGame.toGameView()
    }
    return game.toGameView()
}