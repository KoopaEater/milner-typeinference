package dk.maxkandersen.unification

import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.PairType
import dk.maxkandersen.type.TypeVar
import dk.maxkandersen.type.Type
import java.util.Optional

class RobinsonSet(val terms: Set<Type>) : Unifiable {

    var s: Substitution = emptySubstitution()
    var ts: Set<Type> = terms

    override fun unify(): Substitution {
        while (!isSingleton()) {
            ts = applySubstitution()
        }
        return s
    }

    private fun applySubstitution(): Set<Type> {
        return terms.map { term -> term.substitute(s) }.toSet()
    }

    private fun isSingleton() : Boolean {
        if (terms.size <= 1) return true
        val first = ts.first()
        return terms.all { it == first }
    }

    private fun getAllPaths(): List<DisagreementPath> {
        val lexOrder = Comparator<List<Int>> { a, b ->
            val n = minOf(a.size, b.size)
            for (i in 0 until n) {
                val c = a[i].compareTo(b[i])
                if (c != 0) return@Comparator c
            }
            a.size.compareTo(b.size)
        }

        return terms
            .flatMap { term -> term.getSubPaths() }
            .distinct()
            .sortedWith(lexOrder)
    }

    private fun getDisagreementPath(): Optional<DisagreementPath> {
        if (terms.size <= 1) return Optional.empty()
        for (path in getAllPaths()) {
            // TODO: THIS IS WRONG! BUT CONTINUE HERE
            val heads = terms.map { term -> term.getSubtermAt(path) }
            val allSame = heads.all { head -> head == heads.first() }
            if (!allSame) return Optional.of(path)
        }
        return Optional.empty()
    }

    private fun getDisagreementSet(): Set<Type> {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        val contentsString = terms.joinToString()
        return "{$contentsString}"
    }
}