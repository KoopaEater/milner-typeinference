package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.type.IntType
import dk.maxkandersen.unification.emptySubstitution

data class IntExp(val value: Int) : Expression {
    override fun inferType(te: TypeEnvironment): InferResult {
        return emptySubstitution() to IntType
    }
}