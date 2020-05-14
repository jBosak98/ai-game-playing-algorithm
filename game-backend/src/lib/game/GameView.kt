package lib.game

import lib.pawns.PawnView
import lib.Team.Team
import lib.gameConfig.GameConfig

data class GameView (
    val pawns:List<PawnView>,
    val nextMove: Team,
    val isFinished:Boolean,
    val winner:Team? = null,
    val config: GameConfig?
)

