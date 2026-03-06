package dk.maxkandersen.environment

import dk.maxkandersen.type.QuantifyingTypeScheme
import dk.maxkandersen.type.Type
import dk.maxkandersen.type.TypeScheme
import dk.maxkandersen.type.TypeVar
import dk.maxkandersen.unification.Substitution

typealias Var = String
typealias TypeEnvironment = Map<Var, TypeScheme>

fun emptyTypeEnvironment() : TypeEnvironment = emptyMap()
fun typeEnvironmentOf(vararg pairs: Pair<Var, TypeScheme>) : TypeEnvironment = pairs.toMap()

fun TypeEnvironment.substitute(substitution: Substitution) : TypeEnvironment {
    return this.mapValues { (_, typeScheme) -> typeScheme.substitute(substitution) }
}

fun TypeEnvironment.freeVars() : Set<TypeVar> {
    return this.values.flatMap { typeScheme -> typeScheme.freeVars() }.toSet()
}

fun TypeEnvironment.closure(type: Type): TypeScheme {
    val quantifiers = type.freeVars() - this.freeVars()
    return QuantifyingTypeScheme(quantifiers.toList(), type)
}
