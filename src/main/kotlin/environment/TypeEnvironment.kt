package dk.maxkandersen.environment

import dk.maxkandersen.type.TypeScheme

typealias Var = String
typealias TypeEnvironment = Map<Var, TypeScheme>

fun emptyTypeEnvironment() : TypeEnvironment = emptyMap()
fun typeEnvironmentOf(vararg pairs: Pair<Var, TypeScheme>) : TypeEnvironment = pairs.toMap()