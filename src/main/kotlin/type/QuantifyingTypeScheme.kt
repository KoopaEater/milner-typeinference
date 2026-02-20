package dk.maxkandersen.type

data class QuantifyingTypeScheme(
    val quantifier: TypeVar,
    val type: TypeScheme
) : TypeScheme {
    override fun toString(): String {
        return "\\/$quantifier.$type"
    }
}