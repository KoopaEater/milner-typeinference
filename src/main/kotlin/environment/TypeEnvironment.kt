package dk.maxkandersen.environment

import dk.maxkandersen.type.TypeScheme
import dk.maxkandersen.unification.Substitution

typealias Var = String
typealias TypeEnvironment = Map<Var, TypeScheme>

fun emptyTypeEnvironment() : TypeEnvironment = emptyMap()
fun typeEnvironmentOf(vararg pairs: Pair<Var, TypeScheme>) : TypeEnvironment = pairs.toMap()

fun TypeEnvironment.substitute(substitution: Substitution) : TypeEnvironment {
    return this.mapValues { (_, typeScheme) -> typeScheme.substitute(substitution) }
}