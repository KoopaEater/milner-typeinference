package dk.maxkandersen.type

data class QuantifyingTypeScheme(
    val quantifier: List<TypeVar>,
    val type: TypeScheme
) : TypeScheme {
    override fun toString(): String {
        val quantifiers = quantifier.joinToString { it.sym }
        return "\\/$quantifiers.$type"
    }
}