package dk.maxkandersen.type

interface TypeScheme {
    val quantifiers: List<TypeVar>
    val type: Type
    override fun toString(): String
}