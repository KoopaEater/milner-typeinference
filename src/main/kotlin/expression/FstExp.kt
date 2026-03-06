package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.type.PairType
import dk.maxkandersen.type.TypeVar
import dk.maxkandersen.unification.Constraint
import dk.maxkandersen.unification.compose

data class FstExp(val exp: Expression) : Expression {
    override fun inferTypeW(te: TypeEnvironment): InferWResult {
        val expRes = exp.inferTypeW(te)
        val a = TypeVar()
        val b = TypeVar()
        val s2 = Constraint(expRes.type, PairType(a, b)).unify()
        val s = expRes.substitution compose s2
        return s to a.substitute(s2)
    }
}