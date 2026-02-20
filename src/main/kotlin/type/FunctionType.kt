package dk.maxkandersen.type

data class FunctionType(
    val from: Type,
    val to: Type,
) : Type {
    override fun toString(): String {
        return "$from -> $to"
    }
}