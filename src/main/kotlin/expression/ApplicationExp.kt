package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.environment.substitute
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.Type
import dk.maxkandersen.type.TypeVar
import dk.maxkandersen.unification.compose

data class ApplicationExp(val funExp: Expression, val paramExp: Expression) : Expression {
    override fun inferTypeW(te: TypeEnvironment): InferWResult {
        val funRes = funExp.inferTypeW(te)
        val paramRes = paramExp.inferTypeW(te.substitute(funRes.substitution))
        val a = TypeVar()
        val funType = FunctionType(paramRes.type, a)
        val s3 = funRes.type.substitute(paramRes.substitution) unify funType
        val s = funRes.substitution compose paramRes.substitution compose s3
        return s to a.substitute(s3)
    }

    override fun inferTypeUF(te: TypeEnvironment): Type {
        val funType = funExp.inferTypeUF(te)
        val paramType = paramExp.inferTypeUF(te)
        val a = TypeVar()
        val expectedFunType = FunctionType(paramType, a)
        funType union expectedFunType
        return a.baseType()
    }
}