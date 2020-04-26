package model

import lib.pawns.Pawn

data class Game (
    val pawns:HashMap<Position, Pawn>,
    val nextMove:Team,
    val isFinished:Boolean,
    val possibleMoves: HashMap<Position,List<Position>>
)