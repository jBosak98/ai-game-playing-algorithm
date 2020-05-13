package lib.game

import lib.pawns.Pawn
import lib.position.Position
import lib.Team.Team

data class Game (
    val pawns:Map<Position, Pawn>,
    val nextMove: Team,
    val isFinished:Boolean,
    val possibleMoves: Map<Position,List<PossibleMove>>,
    val winner:Team? = null
)