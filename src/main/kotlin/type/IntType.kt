package dk.maxkandersen.type

import dk.maxkandersen.unification.DisagreementPath
import dk.maxkandersen.unification.Substitution

object IntType : Type {
    override fun toString(): String {
        return "int"
    }

    override fun substitute(substitution: Substitution): Type {
        return IntType
    }

    override fun includes(typeVar: TypeVar): Boolean {
        return false
    }

    //////// ROBINSON UNIFICATION ////////

    override fun toTermString(): String {
        return "int"
    }

    override fun getSubPaths(): List<DisagreementPath> {
        return listOf(emptyList())
    }

    override fun getSubtermAt(path: DisagreementPath): Type {
        if (path.isEmpty()) return this
        throw IllegalArgumentException("Int type has no subterms")
    }

    override fun hasSameTopSymbolAs(other: Type): Boolean {
        return other is IntType
    }

    override fun compareTo(other: Type): Int {
        return when (other) {
            is TypeVar -> 1
            is BoolType -> 1
            is IntType -> 0
            else -> -1
        }
    }
}