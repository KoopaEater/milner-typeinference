package dk.maxkandersen.type

import dk.maxkandersen.constraint.Substitution

data class FunctionType(
    val from: Type,
    val to: Type,
) : Type {
    override fun toString(): String {
        return "$from -> $to"
    }

    override fun substitute(substitution: Substitution): Type {
        return FunctionType(from.substitute(substitution), to.substitute(substitution))
    }

    override fun includes(typeVar: TypeVar): Boolean {
        return from.includes(typeVar) || to.includes(typeVar)
    }
}