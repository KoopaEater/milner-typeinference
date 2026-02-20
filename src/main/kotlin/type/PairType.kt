package dk.maxkandersen.type

import dk.maxkandersen.constraint.Substitution

data class PairType(
    val left: Type,
    val right: Type,
) : Type {
    override fun toString(): String {
        return "$left x $right"
    }

    override fun substitute(substitution: Substitution): Type {
        return PairType(left.substitute(substitution), right.substitute(substitution))
    }

    override fun includes(typeVar: TypeVar): Boolean {
        return left.includes(typeVar) || right.includes(typeVar)
    }
}