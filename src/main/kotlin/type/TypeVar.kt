package dk.maxkandersen.type

data class TypeVar(
    val sym: String
) : Type {
    constructor() : this("'${fresh()}")

    companion object {
        private var counter = 0
        private fun fresh(): String {
            val sym = "t$counter"
            counter++
            return sym
        }
    }

    override fun toString(): String {
        return sym
    }
}