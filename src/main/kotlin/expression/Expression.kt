package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.type.Type

interface Expression {
    fun inferTypeW(te: TypeEnvironment): InferWResult
    fun inferTypeUF(te: TypeEnvironment): Type
}