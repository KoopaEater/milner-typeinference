package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.environment.substitute
import dk.maxkandersen.type.PairType
import dk.maxkandersen.unification.compose

data class PairExp(val left: Expression, val right: Expression) : Expression {
    override fun inferType(te: TypeEnvironment): InferResult {
        val leftRes = left.inferType(te)
        val rightRes = right.inferType(te.substitute(leftRes.substitution))
        val s = leftRes.substitution compose rightRes.substitution
        val leftType = leftRes.type.substitute(rightRes.substitution)
        return s to PairType(leftType, rightRes.type)
    }
}