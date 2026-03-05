package dk.maxkandersen.type

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.environment.freeVars
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
        fun reset() {
            counter = 0
        }
    }

    override fun toString(): String {
        return sym
    }

    override fun substitute(substitution: Substitution): Type {
        return substitution[this] ?: return this
    }

    override fun freeVars(): Set<TypeVar> {
        return setOf(this)
    }

    override fun includes(typeVar: TypeVar): Boolean {
        return this == typeVar
    }

    //////// ROBINSON UNIFICATION ////////

    override fun toTermString(): String {
        return sym
    }

    override fun getSubPaths(): List<DisagreementPath> {
        return listOf(emptyList())
    }

    override fun getSubtermAt(path: DisagreementPath): Type {
        if (path.isEmpty()) return this
        throw IllegalArgumentException("TypeVar has no subterms")
    }

    override fun hasSameTopSymbolAs(other: Type): Boolean {
        return other is TypeVar && sym == other.sym
    }

    override fun compareTo(other: Type): Int {
        return when (other) {
            is TypeVar -> sym.compareTo(other.sym)
            else -> -1
        }
    }
}