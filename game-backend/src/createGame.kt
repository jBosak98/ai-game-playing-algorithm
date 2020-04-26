import algorithm.getPossibleMoves
import model.Game
import lib.pawns.Pawn
import model.Position
import model.Team

fun createGame(): Game {
    val pawns = HashMap<Position, Pawn>()
    pawns[Position(0,0)] = Pawn(0, 0, Team.BLACK)
    pawns[Position(0,2)] = Pawn(0, 2, Team.BLACK)
    pawns[Position(0,4)] = Pawn(0, 4, Team.BLACK)
    pawns[Position(0,6)] = Pawn(0, 6, Team.BLACK)
    pawns[Position(1,1)] = Pawn(1, 1, Team.BLACK)
    pawns[Position(1,3)] = Pawn(1, 3, Team.BLACK)
    pawns[Position(1,5)] = Pawn(1, 5, Team.BLACK)
    pawns[Position(1,7)] = Pawn(1, 7, Team.BLACK)
    pawns[Position(2,0)] = Pawn(2, 0, Team.BLACK)
    pawns[Position(2,2)] = Pawn(2, 2, Team.BLACK)
    pawns[Position(2,4)] = Pawn(2, 4, Team.BLACK)
    pawns[Position(2,6)] = Pawn(2, 6, Team.BLACK)

    pawns[Position(7,1)] = Pawn(7, 1, Team.WHITE)
    pawns[Position(7,3)] = Pawn(7, 3, Team.WHITE)
    pawns[Position(7,5)] = Pawn(7, 5, Team.WHITE)
    pawns[Position(7,7)] = Pawn(7, 7, Team.WHITE)
    pawns[Position(6,0)] = Pawn(6, 0, Team.WHITE)
    pawns[Position(6,2)] = Pawn(6, 2, Team.WHITE)
    pawns[Position(6,4)] = Pawn(6, 4, Team.WHITE)
    pawns[Position(6,6)] = Pawn(6, 6, Team.WHITE)
    pawns[Position(5,1)] = Pawn(5, 1, Team.WHITE)
    pawns[Position(5,3)] = Pawn(5, 3, Team.WHITE)
    pawns[Position(5,5)] = Pawn(5, 5, Team.WHITE)
    pawns[Position(5,7)] = Pawn(5, 7, Team.WHITE)

    return Game(
        pawns = pawns,
        nextMove = Team.WHITE,
        isFinished = false,
        possibleMoves = getPossibleMoves(pawns,null)
    )
}