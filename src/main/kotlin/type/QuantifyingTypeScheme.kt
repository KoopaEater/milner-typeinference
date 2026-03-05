package dk.maxkandersen.type

import dk.maxkandersen.unification.Substitution
import dk.maxkandersen.unification.region

data class QuantifyingTypeScheme(
    override val quantifiers: List<TypeVar>,
    override val type: Type
) : TypeScheme {
    override fun toString(): String {
        val quantifierStr = quantifiers.joinToString { it.sym }
        return "\\/$quantifierStr.$type"
    }

    override fun substitute(substitution: Substitution): TypeScheme {
        val S0 = substitution.filterKeys { typeVar -> typeVar in freeVars() }
        val invalidQuantifiers = quantifiers.filter { quantifier -> quantifier in S0.region() }
        val conversion = invalidQuantifiers.associateWith { TypeVar() }
        val newTypeScheme = this.alphaConvert(conversion)
        val newType = newTypeScheme.type.substitute(substitution)
        return QuantifyingTypeScheme(newTypeScheme.quantifiers, newType)
    }

    override fun freeVars(): Set<TypeVar> {
        return type.freeVars() - quantifiers.toSet()
    }

    override fun alphaConvert(conversion: Map<TypeVar, TypeVar>): TypeScheme {
        conversion.forEach { (from, to) -> if (to in freeVars()) throw InvalidAlphaConversionException(from, to, this) }
        val newQuantifiers = quantifiers.map { quantifier -> conversion[quantifier] ?: quantifier }
        val newType = type.substitute(conversion)
        return QuantifyingTypeScheme(newQuantifiers, newType)
    }
}