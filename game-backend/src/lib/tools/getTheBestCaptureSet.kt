package lib.tools

fun<T> getTheBestCaptureSet(accKills:Int, itemKills:Int, item:T, acc:List<T>): List<T> {
    return when {
        accKills < itemKills -> listOf(item)
        accKills == itemKills -> acc + item
        else -> acc
    }
}
