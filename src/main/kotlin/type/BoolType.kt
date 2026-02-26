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
        return emptyList()
    }

    override fun getSubtermAt(path: DisagreementPath): Type {
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
}