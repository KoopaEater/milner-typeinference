package dk.maxkandersen.type

data class PairType(
    val left: Type,
    val right: Type,
) : Type {
    override fun toString(): String {
        return "$left x $right"
    }
}