package model

data class Position(
    val row:Int,
    val column:Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (row != other.row) return false
        if (column != other.column) return false

        return true
    }


    override fun hashCode(): Int = 31 * row + column
    override fun toString(): String {
        return (column + (row * 8)).toString()
    }
}