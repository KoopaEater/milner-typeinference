package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.type.Type
import dk.maxkandersen.unification.Substitution

data class FstExp(val exp: Expression) : Expression {
    override fun inferType(te: TypeEnvironment): InferResult {
        TODO("Not yet implemented")
    }
}