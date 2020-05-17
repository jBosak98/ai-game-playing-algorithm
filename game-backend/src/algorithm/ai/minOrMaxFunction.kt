package algorithm.ai

import kotlin.math.max
import kotlin.math.min

fun minOrMaxFunction(isMax: Boolean): (Int, Int) -> Int {
    return fun(score: Int, secondScore): Int =
        when (isMax) {
            true -> max(score, secondScore)
            else -> min(score, secondScore)
        }
}