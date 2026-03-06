package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.environment.substitute
import dk.maxkandersen.type.PairType
import dk.maxkandersen.unification.compose

data class PairExp(val left: Expression, val right: Expression) : Expression {
    override fun inferTypeW(te: TypeEnvironment): InferWResult {
        val leftRes = left.inferTypeW(te)
        val rightRes = right.inferTypeW(te.substitute(leftRes.substitution))
        val s = leftRes.substitution compose rightRes.substitution
        val leftType = leftRes.type.substitute(rightRes.substitution)
        return s to PairType(leftType, rightRes.type)
    }
}