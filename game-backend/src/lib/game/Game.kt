package lib.game

import lib.pawns.Pawn
import model.Position
import model.Team

data class Game (
    val pawns:HashMap<Position, Pawn>,
    val nextMove: Team,
    val isFinished:Boolean,
    val possibleMoves: HashMap<Position,List<Position>>
)