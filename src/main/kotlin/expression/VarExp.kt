package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.environment.UnknownVariableException
import dk.maxkandersen.environment.Var
import dk.maxkandersen.unification.emptySubstitution

data class VarExp(val sym: Var): Expression {
    override fun inferTypeW(te: TypeEnvironment): InferResult {
        val envType = te[sym] ?: throw UnknownVariableException(sym)
        return emptySubstitution() to envType.instantiate()
    }
}