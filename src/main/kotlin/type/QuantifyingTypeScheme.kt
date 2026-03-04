package dk.maxkandersen.type

data class QuantifyingTypeScheme(
    override val quantifiers: List<TypeVar>,
    override val type: Type
) : TypeScheme {
    override fun toString(): String {
        val quantifierStr = quantifiers.joinToString { it.sym }
        return "\\/$quantifierStr.$type"
    }
}