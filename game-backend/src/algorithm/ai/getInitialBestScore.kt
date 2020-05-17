package algorithm.ai

fun getInitialBestScore(isAlphaBeta:Boolean, isMax:Boolean, alpha:Int? = null, beta:Int? = null): Int {
    return  when (isAlphaBeta) {
        true -> if (isMax) alpha else beta
        else -> if (isMax) Int.MIN_VALUE else Int.MAX_VALUE
    } as Int
}