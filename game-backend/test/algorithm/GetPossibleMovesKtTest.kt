package algorithm

import createGame
import lib.game.Game
import model.Team
import org.junit.Before
import org.junit.Test

internal class GetPossibleMovesKtTest {

    lateinit var game: Game

    @Before
    fun setUp() {
        game = createGame()
    }

    @Test
    fun getPossibleMoves() {
        val x = getPossibleMoves(game.pawns, Team.BLACK)
        println(game)
    }
}