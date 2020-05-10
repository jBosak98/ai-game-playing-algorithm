package lib.Team

fun Team.opposite() = when(this){
    Team.BLACK -> Team.WHITE
    Team.WHITE -> Team.BLACK
}