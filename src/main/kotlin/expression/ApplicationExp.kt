package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.type.Type
import dk.maxkandersen.unification.Substitution

data class ApplicationExp(val funExp: Expression, val paramExp: Expression) : Expression {
    override fun inferType(te: TypeEnvironment): Pair<Substitution, Type> {
        TODO("Not yet implemented")
    }
}