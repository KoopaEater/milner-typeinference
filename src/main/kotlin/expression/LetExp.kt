package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.environment.Var
import dk.maxkandersen.environment.closure
import dk.maxkandersen.environment.substitute
import dk.maxkandersen.unification.compose

data class LetExp(val sym: Var, val assignmentExp: Expression, val body: Expression) : Expression {
    override fun inferTypeW(te: TypeEnvironment): InferWResult {
        val assignmentRes = assignmentExp.inferTypeW(te)
        val s1te = te.substitute(assignmentRes.substitution)
        val paramType = s1te.closure(assignmentRes.type)
        val paramTe = s1te + (sym to paramType)
        val bodyRes = body.inferTypeW(paramTe)
        val s = assignmentRes.substitution compose bodyRes.substitution
        return s to bodyRes.type
    }
}