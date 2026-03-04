package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.type.Type
import dk.maxkandersen.unification.Substitution

interface Expression {
    fun inferType(te: TypeEnvironment): InferResult
}