package dk.maxkandersen.type

import dk.maxkandersen.unification.DisagreementPath
import dk.maxkandersen.unification.Substitution

data class TypeVar(
    val sym: String
) : Type {
    constructor() : this("'${fresh()}")

    companion object {
        private var counter = 0
        private fun fresh(): String {
            val sym = "t$counter"
            counter++
            return sym
        }
    }

    override fun toString(): String {
        return sym
    }

    override fun toTermString(): String {
        return sym
    }

    override fun getSubPaths(): List<DisagreementPath> {
        return emptyList()
    }

    override fun getSubtermAt(path: DisagreementPath): Type {
        throw IllegalArgumentException("TypeVar has no subterms")
    }

    override fun hasSameTopSymbolAs(other: Type): Boolean {
        return other is TypeVar && sym == other.sym
    }

    override fun substitute(substitution: Substitution): Type {
        return substitution[this] ?: return this
    }

    override fun includes(typeVar: TypeVar): Boolean {
        return this == typeVar
    }
}