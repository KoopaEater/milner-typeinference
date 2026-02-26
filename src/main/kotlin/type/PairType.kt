package dk.maxkandersen.type

import dk.maxkandersen.unification.Substitution

data class PairType(
    val left: Type,
    val right: Type,
) : NaryType {
    override val subtypes = arrayOf(left, right)

    override fun toString(): String {
        return "$left x $right"
    }

    override fun toTermString(): String {
        return "p(${left.toTermString()},${right.toTermString()})"
    }

    override fun hasSameTopSymbolAs(other: Type): Boolean {
        return other is PairType
    }

    override fun substitute(substitution: Substitution): Type {
        return PairType(left.substitute(substitution), right.substitute(substitution))
    }
}