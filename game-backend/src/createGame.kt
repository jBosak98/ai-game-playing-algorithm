import model.Game
import model.Pawn

fun createGame(): Game {
    val blackTeam = listOf(
        Pawn(0,0,true),
        Pawn(0,2,true),
        Pawn(0,4,true),
        Pawn(0,6,true),
        Pawn(1,1,true),
        Pawn(1,3,true),
        Pawn(1,5,true),
        Pawn(1,7,true),
        Pawn(2,0,true),
        Pawn(2,2,true),
        Pawn(2,4,true),
        Pawn(2,6,true)
    )
    val whiteTeam = listOf(
        Pawn(7,1,false),
        Pawn(7,3,false),
        Pawn(7,5,false),
        Pawn(7,7,false),
        Pawn(6,0,false),
        Pawn(6,2,false),
        Pawn(6,4,false),
        Pawn(6,6,false),
        Pawn(5,1,false),
        Pawn(5,3,false),
        Pawn(5,5,false),
        Pawn(5,7,false)
    )
    return Game(
        pawns = blackTeam + whiteTeam,
        isBlackTurn = false,
        isFinished = false
    )
}