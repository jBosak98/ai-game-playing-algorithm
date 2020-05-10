import algorithm.getPossibleMoves
import lib.game.Game
import lib.pawns.Pawn
import lib.position.Position
import lib.Team.Team

fun createGame(): Game {
    val pawns:Map<Position,Pawn> = mapOf(


    Position(0, 0) to Pawn(0, 0, Team.BLACK),

    Position(0, 4) to Pawn(0, 4, Team.BLACK),
    Position(0, 6) to Pawn(0, 6, Team.BLACK),
    Position(1, 1) to Pawn(1, 1, Team.BLACK),
    Position(1, 3) to Pawn(1, 3, Team.BLACK),


//        Position(0,2) to Pawn(0, 2, Team.BLACK),
//    Position(1,5) to Pawn(1, 5, Team.BLACK),
//    Position(1,7) to Pawn(1, 7, Team.BLACK),
//    Position(2,0) to Pawn(2, 0, Team.BLACK),
//    Position(2,2) to Pawn(2, 2, Team.BLACK),
//    Position(2,4) to Pawn(2, 4, Team.BLACK),
//    Position(2,6) to Pawn(2, 6, Team.BLACK),
//
//    Position(7,1) to Pawn(7, 1, Team.WHITE),
//    Position(7,3) to Pawn(7, 3, Team.WHITE),
//    Position(7,5) to Pawn(7, 5, Team.WHITE),
//    Position(7,7) to Pawn(7, 7, Team.WHITE),
//    Position(6,0) to Pawn(6, 0, Team.WHITE),
//    Position(6,2) to Pawn(6, 2, Team.WHITE),
//    Position(6,4) to Pawn(6, 4, Team.WHITE),
//    Position(6,6) to Pawn(6, 6, Team.WHITE),


    Position(2, 4) to Pawn(2, 4, Team.WHITE),
    Position(4, 4) to Pawn(4, 4, Team.WHITE),
    Position(4, 2) to Pawn(4, 2, Team.WHITE),
    Position(2, 2) to Pawn(2, 2, Team.WHITE)
    )
    return Game(
        pawns = pawns,
        nextMove = Team.WHITE,
        isFinished = false,
        possibleMoves = getPossibleMoves(pawns)
    )
}