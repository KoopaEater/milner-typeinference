package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.type.BoolType
import dk.maxkandersen.unification.emptySubstitution

data class BoolExp(val value: Boolean) : Expression {
    override fun inferTypeW(te: TypeEnvironment): InferResult {
        return emptySubstitution() to BoolType
    }
}