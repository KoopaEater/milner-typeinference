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

    override fun compareTo(other: Type): Int {
        return when (other) {
            is TypeVar -> 1
            is BoolType -> 1
            is IntType -> 1
            is FunctionType -> {
                val comp1 = this.from.compareTo(other.from)
                if (comp1 != 0) comp1 else this.to.compareTo(other.to)
            }
            else -> -1
        }
    }

}