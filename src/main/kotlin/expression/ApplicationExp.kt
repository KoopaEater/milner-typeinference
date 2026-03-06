package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.environment.substitute
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.TypeVar
import dk.maxkandersen.unification.Constraint
import dk.maxkandersen.unification.compose

data class ApplicationExp(val funExp: Expression, val paramExp: Expression) : Expression {
    override fun inferType(te: TypeEnvironment): InferResult {
        val funRes = funExp.inferType(te)
        val paramRes = paramExp.inferType(te.substitute(funRes.substitution))
        val a = TypeVar()
        val funType = FunctionType(paramRes.type, a)
        val s3 = Constraint(funRes.type.substitute(paramRes.substitution), funType).unify()
        val s = funRes.substitution compose paramRes.substitution compose s3
        return s to a.substitute(s3)
    }
}