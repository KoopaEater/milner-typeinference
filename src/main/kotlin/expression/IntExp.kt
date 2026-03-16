package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.type.IntType
import dk.maxkandersen.type.Type
import dk.maxkandersen.unification.emptySubstitution

data class IntExp(val value: Int) : Expression {
    override fun inferTypeW(te: TypeEnvironment): InferWResult {
        return emptySubstitution() to IntType
    }

    override fun inferTypeUF(te: TypeEnvironment): Type {
        return IntType
    }
}