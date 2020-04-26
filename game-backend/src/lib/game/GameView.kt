package lib.game

import lib.pawns.PawnView
import model.Team

data class GameView (
    val pawns:List<PawnView>,
    val nextMove: Team,
    val isFinished:Boolean
)

