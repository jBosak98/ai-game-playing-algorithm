package lib.Team

fun Team.opposite() = when(this){
    Team.BLACK -> Team.WHITE
    Team.WHITE -> Team.BLACK
}

fun Team.firstRow() = when(this){
    Team.BLACK -> 0
    Team.WHITE -> 7
}