package dk.maxkandersen.type

import dk.maxkandersen.unification.Substitution

data class FunctionType(
    val from: Type,
    val to: Type,
) : NaryType {

    override val subtypes = arrayOf(from, to)

    override fun toString(): String {
        return "$from -> $to"
    }

    override fun toTermString(): String {
        return "f(${from.toTermString()},${to.toTermString()})"
    }

    override fun hasSameTopSymbolAs(other: Type): Boolean {
        return other is FunctionType
    }

    override fun substitute(substitution: Substitution): Type {
        return FunctionType(from.substitute(substitution), to.substitute(substitution))
    }

}