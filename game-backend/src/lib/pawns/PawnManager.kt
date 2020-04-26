package lib.pawns

import model.Position
import model.Team

fun Pawns.isFieldOccupied(position: Position) =
    isFieldOccupied(position.row, position.column)

fun Pawns.isFieldOccupied(row:Int, column:Int) =
    any { it.key.row == row && it.key.column == column }


fun Pawns.getPawns(): Map<Position, Pawn> = getPawns(null)

fun Pawns.getPawns(team: Team?): Map<Position, Pawn> =
    when (team){
        Team.WHITE -> this.filter { it.value.team === Team.WHITE }
        Team.BLACK -> this.filter { it.value.team === Team.BLACK }
        else -> this
    }

