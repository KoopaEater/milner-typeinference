package dk.maxkandersen.type

import dk.maxkandersen.unification.robinson.DisagreementPath
import dk.maxkandersen.unification.Substitution
import dk.maxkandersen.unification.unionfind.InvalidUnionException

@ConsistentCopyVisibility
data class TypeVar private constructor(
    val sym: String
) : Type {

    companion object {
        private val cache = mutableMapOf<String, TypeVar>()
        operator fun invoke(sym: String) = cache.getOrPut(sym) { TypeVar(sym) }
        operator fun invoke() = invoke("'${fresh()}")

        private var counter = 0
        private fun fresh(): String {
            val sym = "t$counter"
            counter++
            return sym
        }
        fun reset() {
            counter = 0
            cache.clear()
            for (typeVar in cache.values) {
                typeVar.ufParent = null
            }
        }
    }

    override fun toString(): String {
        return sym
    }

    override fun substitute(substitution: Substitution): Type {
        return substitution[this] ?: this
    }

    override fun freeVars(): Set<TypeVar> {
        return setOf(this)
    }

    override fun includes(typeVar: TypeVar): Boolean {
        return this == typeVar
    }

    //////// UNION-FIND ////////

    var ufParent: Type? = null

    override fun baseType(): Type {
        ufParent = ufParent?.baseType() // Path compression
        return ufParent ?: this
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