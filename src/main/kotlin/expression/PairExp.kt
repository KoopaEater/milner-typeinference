package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment

data class PairExp(val left: Expression, val right: Expression) : Expression {
    override fun inferType(te: TypeEnvironment): InferResult {
        TODO("Not yet implemented")
    }
}