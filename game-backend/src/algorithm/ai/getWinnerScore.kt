package algorithm.ai

import lib.Team.Team

fun getWinnerScore(winner: Team?, player: Team, depth: Int): Int =
    when (winner) {
        player -> Int.MAX_VALUE / depth
        else -> Int.MIN_VALUE / depth
    }