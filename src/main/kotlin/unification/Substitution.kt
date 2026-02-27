package dk.maxkandersen.unification

import dk.maxkandersen.type.Type
import dk.maxkandersen.type.TypeVar

typealias Substitution = Map<TypeVar, Type>
fun substitutionOf(vararg pairs: Pair<TypeVar, Type>) = pairs.toMap()
fun emptySubstitution() = emptyMap<TypeVar, Type>()

fun Substitution.compose(new: Substitution): Substitution {
    return this.mapValues { (_, rhs) -> rhs.substitute(new) } + new
}