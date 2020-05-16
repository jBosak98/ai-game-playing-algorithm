package algorithm.ai

import lib.Team.Team
import lib.game.Game
import lib.game.Move

//fun alphaBeta(game: Game, team: Team, maxDepth: Int): Move {
//    val moves = getMoves(game)
////    val bestMoves = moves.fold(listOf<Pair<Move, Int>>()) { acc, move ->
////        val score = minmax(game, move, 1, team, maxDepth)
////        when {
////            acc.isEmpty() -> listOf(Pair(move, score))
////            acc.first().second < score -> listOf(Pair(move, score))
////            acc.first().second == score -> acc + Pair(move, score)
////            acc.first().second > score -> acc
////            else -> listOf(Pair(move, score))
////        }
////
////    }
//}