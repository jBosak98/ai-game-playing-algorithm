package algorithm


import junit.framework.Assert.assertFalse
import lib.game.Game
import lib.Team.Team
import lib.pawns.Pawn
import model.Position
import org.junit.Before
import org.junit.Test

internal class GetPossibleMovesKtTest {

    lateinit var game: Game

    private fun createGame(): Game {
        val pawns:Map<Position, Pawn> = mapOf(
            Position(0,0) to Pawn(0, 0, Team.BLACK),
            Position(0,4) to Pawn(0, 4, Team.BLACK),
            Position(0,6) to Pawn(0, 6, Team.BLACK),
            Position(1,1) to Pawn(1, 1, Team.BLACK),
            Position(1,3) to Pawn(1, 3, Team.BLACK),
            Position(2,4) to Pawn(2, 4, Team.WHITE),
            Position(4,4) to Pawn(4, 4, Team.WHITE),
            Position(4,2) to Pawn(4, 2, Team.WHITE),
            Position(2,2) to Pawn(2, 2, Team.WHITE)
        )
        return Game(
            pawns = pawns,
            nextMove = Team.WHITE,
            isFinished = false,
            possibleMoves = getPossibleMoves(pawns)
        )
    }

    @Before
    fun setUp() {
        game = createGame()
    }

    @Test
    fun getPossibleMoves() {
//        val x = getPossibleMoves(game.pawns, Team.BLACK)
//        println(game)
    }

    @Test
    fun filterCapturedPawnTest(){
        val pawns = filterCapturedPawn(Position(1,3), Position(3,5), game.pawns)
        assertFalse(pawns.containsKey(Position(2,4)))

    }
}