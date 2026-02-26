package dk.maxkandersen.type

import dk.maxkandersen.unification.DisagreementPath
import dk.maxkandersen.unification.Substitution

object IntType : Type {
    override fun toString(): String {
        return "int"
    }

    override fun toTermString(): String {
        return "int"
    }

    override fun getSubPaths(): List<DisagreementPath> {
        return emptyList()
    }

    override fun getSubtermAt(path: DisagreementPath): Type {
        throw IllegalArgumentException("Int type has no subterms")
    }

    override fun hasSameTopSymbolAs(other: Type): Boolean {
        return other is IntType
    }

    override fun substitute(substitution: Substitution): Type {
        return IntType
    }

    override fun includes(typeVar: TypeVar): Boolean {
        return false
    }
}