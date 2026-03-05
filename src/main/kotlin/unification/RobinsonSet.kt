package dk.maxkandersen.unification

import dk.maxkandersen.type.Type
import dk.maxkandersen.type.TypeVar
import java.util.Optional

class RobinsonSet(vararg terms: Type) : Unifiable {

    var S: Substitution = emptySubstitution()
    var Ts: List<Type> = terms.sorted()

    override fun unify(): Substitution {
        while (true) {
            val D = getDisagreementPair()
            if (D.isEmpty) break
            val s = D.get().first
            val t = D.get().second
            if (s !is TypeVar || t.includes(s)) throw RobinsonUnificationException(s, t)
            S = S compose substitutionOf(s to t)
            Ts = applySubstitution().sorted()
        }
        return S
    }

    private fun applySubstitution(): Set<Type> {
        return Ts.map { term -> term.substitute(S) }.toSet()
    }

    fun getAllPaths(): List<DisagreementPath> {
        val lexOrder = Comparator<List<Int>> { a, b ->
            val n = minOf(a.size, b.size)
            for (i in 0 until n) {
                val c = a[i].compareTo(b[i])
                if (c != 0) return@Comparator c
            }
            a.size.compareTo(b.size)
        }

        return Ts
            .flatMap { term -> term.getSubPaths() }
            .distinct()
            .sortedWith(lexOrder)
    }

    fun getDisagreementPair(): Optional<Pair<Type, Type>> {
        if (Ts.size <= 1) return Optional.empty()
        for (path in getAllPaths()) {
            val heads = Ts.map { term -> term.getSubtermAt(path) }
            for (head in heads) {
                if (!head.hasSameTopSymbolAs(heads.first())) {
                    return Optional.of(heads.first() to head)
                }
            }
        }
        return Optional.empty()
    }

    override fun toString(): String {
        val contentsString = Ts.joinToString { term -> term.toTermString() }
        return "{$contentsString}"
    }

}