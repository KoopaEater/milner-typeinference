package dk.maxkandersen.constraint

import dk.maxkandersen.type.Type
import dk.maxkandersen.type.TypeVar

typealias Substitution = Map<TypeVar, Type>
fun substitutionOf(vararg pairs: Pair<TypeVar, Type>) = pairs.toMap()
fun emptySubstitution() = emptyMap<TypeVar, Type>()