package lib.game

import lib.pawns.PawnView
import lib.Team.Team

data class GameView (
    val pawns:List<PawnView>,
    val nextMove: Team,
    val isFinished:Boolean,
    val winner:Team? = null
)

