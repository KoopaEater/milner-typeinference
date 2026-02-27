package dk.maxkandersen.type

import dk.maxkandersen.unification.Substitution

data class PairType(
    val left: Type,
    val right: Type,
) : NaryType {
    override val subtypes = arrayOf(left, right)

    override fun toString(): String {
        return "($left x $right)"
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

    override fun compareTo(other: Type): Int {
        return when (other) {
            is TypeVar -> 1
            is BoolType -> 1
            is IntType -> 1
            is FunctionType -> 1
            is PairType -> {
                val comp1 = this.left.compareTo(other.left)
                if (comp1 != 0) comp1 else this.right.compareTo(other.right)
            }
            else -> -1
        }
    }
}