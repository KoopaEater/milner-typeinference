package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.environment.Var
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.TypeVar

data class LambdaExp(val param: Var, val body: Expression) : Expression {
    override fun inferTypeW(te: TypeEnvironment): InferResult {
        val a = TypeVar()
        val te1 = te + (param to a)
        val bodyRes = body.inferTypeW(te1)
        val s1 = bodyRes.substitution
        val t1 = bodyRes.type
        val s1a = a.substitute(s1)
        return s1 to FunctionType(s1a, t1)
    }
}