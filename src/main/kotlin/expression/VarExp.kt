package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.environment.UnknownVariableException
import dk.maxkandersen.environment.Var
import dk.maxkandersen.type.Type
import dk.maxkandersen.unification.emptySubstitution

data class VarExp(val sym: Var): Expression {
    override fun inferTypeW(te: TypeEnvironment): InferWResult {
        val envType = te[sym] ?: throw UnknownVariableException(sym)
        return emptySubstitution() to envType.instantiate()
    }

    override fun inferTypeUF(te: TypeEnvironment): Type {
        val envType = te[sym] ?: throw UnknownVariableException(sym)
        return envType.instantiate().baseType()
    }
}