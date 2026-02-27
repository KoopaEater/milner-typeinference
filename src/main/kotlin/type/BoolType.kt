package dk.maxkandersen.type

import dk.maxkandersen.unification.DisagreementPath
import dk.maxkandersen.unification.Substitution

object BoolType : Type {
    override fun toString(): String {
        return "bool"
    }

    override fun toTermString(): String {
        return "bool"
    }

    override fun getSubPaths(): List<DisagreementPath> {
        return listOf(emptyList())
    }

    override fun getSubtermAt(path: DisagreementPath): Type {
        if (path.isEmpty()) return this
        throw IllegalArgumentException("Bool type has no subterms")
    }

    override fun hasSameTopSymbolAs(other: Type): Boolean {
        return other is BoolType
    }

    override fun substitute(substitution: Substitution): Type {
        return BoolType
    }

    override fun includes(typeVar: TypeVar): Boolean {
        return false
    }

    override fun compareTo(other: Type): Int {
        return when (other) {
            is TypeVar -> 1
            is BoolType -> 0
            else -> -1
        }
    }
}