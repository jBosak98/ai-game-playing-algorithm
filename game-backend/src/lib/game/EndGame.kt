package lib.game

import lib.Team.Team
import lib.pawns.Pawns
import lib.pawns.getPawns
import lib.position.InitPosition

fun getWinner(pawns: Pawns, possibleMoves: Map<InitPosition, PossibleMoves>): Team? =
    when{
        pawns.getPawns(Team.BLACK).isEmpty() -> Team.WHITE
        pawns.getPawns(Team.WHITE).isEmpty() -> Team.BLACK
        pawns.getPawns(Team.WHITE).filterNot { possibleMoves[it.key].isNullOrEmpty() }.isEmpty() -> Team.BLACK
        pawns.getPawns(Team.BLACK).filterNot { possibleMoves[it.key].isNullOrEmpty() }.isEmpty()  -> Team.WHITE
        else -> null
    }

fun isFinished(pawns:Pawns, possibleMoves: Map<InitPosition, PossibleMoves>):Boolean{
    return  pawns.getPawns(Team.BLACK).isEmpty() ||
            pawns.getPawns(Team.WHITE).isEmpty() ||
            pawns.getPawns(Team.WHITE).filterNot { possibleMoves[it.key].isNullOrEmpty() }.isEmpty() ||
            pawns.getPawns(Team.BLACK).filterNot { possibleMoves[it.key].isNullOrEmpty() }.isEmpty()
}

