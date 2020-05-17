package algorithm.ai

sealed class GameAlgorithmInput {
    data class AlphaBetaInput(val alpha: Int, val beta: Int, val score: Int) : GameAlgorithmInput()
    data class MinMaxInput(val score: Int) : GameAlgorithmInput()
}