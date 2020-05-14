package lib.game

data class GameWithMove(
    val board: GameView,
    val move: Move?
)