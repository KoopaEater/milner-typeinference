package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.environment.Var

data class LambdaExp(val param: Var, val body: Expression) : Expression {
    override fun inferType(te: TypeEnvironment): InferResult {
        TODO("Not yet implemented")
    }
}