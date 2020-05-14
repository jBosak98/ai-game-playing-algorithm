package lib.gameConfig

import lib.Team.Team

data class PlayerConfig(
    val team: Team,
    val playerType: PlayerType,
    val algorithmType: AlgorithmType?,
    val depth: Int?
)