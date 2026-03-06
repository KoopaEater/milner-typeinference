package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment

interface Expression {
    fun inferTypeW(te: TypeEnvironment): InferResult
}