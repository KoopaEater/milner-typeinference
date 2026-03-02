package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.environment.Var
import dk.maxkandersen.type.Type
import dk.maxkandersen.unification.Substitution

data class VarExp(val sym: Var): Expression {
    override fun inferType(te: TypeEnvironment): Pair<Substitution, Type> {
        TODO("Not yet implemented")
    }
}