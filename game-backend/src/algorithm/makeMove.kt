package algorithm

import lib.Team.Team
import lib.Team.opposite
import lib.game.*
import lib.gameConfig.GameConfig
import lib.gameConfig.PlayerType
import lib.pawns.makeKing
import lib.pawns.shouldChangeToKing
import lib.pawns.toPosition

fun makeMove(gameWithMove: GameWithMove): GameView {
    val game = gameWithMove.toGame()
    val move = gameWithMove.move

    return when {
        move != null && gameWithMove.isValid() && move.isValid(game) -> makeMove(game, move).toGameView()
        else -> game.toGameView()
    }
}

fun shouldAIMakeMove(game:Game): Boolean {
    return game
        .config
        ?.players
        ?.find { it.team == game.nextMove }
        ?.playerType == PlayerType.COMPUTER
}
fun shouldAIMakeMove(config:GameConfig, nextMove: Team): Boolean =
    config
        .players
        .find { it.team == nextMove }
        ?.playerType == PlayerType.COMPUTER



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

    val possibleMoves = getPossibleMoves(pawns)
    val isFinished = isFinished(pawns, possibleMoves)

    return Game(
        pawns = pawns,
        nextMove = game.nextMove.opposite(),
        isFinished = isFinished,
        possibleMoves = possibleMoves,
        winner = if(isFinished) getWinner(pawns, possibleMoves) else null,
        config = game.config
    )
}
