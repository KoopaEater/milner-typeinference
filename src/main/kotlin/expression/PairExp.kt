package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.type.Type
import dk.maxkandersen.unification.Substitution

data class PairExp(val left: Expression, val right: Expression) : Expression {
    override fun inferType(te: TypeEnvironment): Pair<Substitution, Type> {
        TODO("Not yet implemented")
    }
}