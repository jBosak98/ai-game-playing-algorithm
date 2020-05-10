package lib.list

fun <E>List<E>.maybeFirst() =
    if(this.isEmpty()) null else this.first()