package lib.pawns

import model.Position
import lib.Team.Team

fun Pawns.isFieldOccupied(position: Position) =
    isFieldOccupied(position.row, position.column)

fun Pawns.isFieldOccupied(row:Int, column:Int) =
    any { it.key.row == row && it.key.column == column }

fun Pawns.fieldOccupiedByTeam(position: Position) =
    this[position]?.team

fun Pawns.fieldOccupiedByTeam(row:Int, column:Int) =
    fieldOccupiedByTeam(Position(row, column))

fun Pawn.getPosition() =
    Position(this.row, this.column)

fun Pawns.getPawns(): Map<Position, Pawn> = getPawns(null)

fun Pawns.getPawns(team: Team?): Map<Position, Pawn> =
    when (team){
        Team.WHITE -> this.filter { it.value.team === Team.WHITE }
        Team.BLACK -> this.filter { it.value.team === Team.BLACK }
        else -> this
    }

fun Pawn.toPawnView(possibleMoves:List<Position>) =
        PawnView(
            row = this.row,
            column = this.column,
            team = this.team,
            isKing = this.isKing,
            possibleMoves = possibleMoves
        )
