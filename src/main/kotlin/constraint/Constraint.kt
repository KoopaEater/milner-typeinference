package dk.maxkandersen.constraint

import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.PairType
import dk.maxkandersen.type.Type
import dk.maxkandersen.type.TypeVar


class UnificationException(constraint: Constraint) : RuntimeException("Unification error: Could not unify $constraint")

data class Constraint(val left: Type, val right: Type) {

    fun unify(): Substitution {
        return when {
            left == right -> emptySubstitution()
            left is TypeVar && !(right.includes(left)) -> substitutionOf(left to right)
            right is TypeVar && !(left.includes(right)) -> substitutionOf(right to left)
            left is FunctionType && right is FunctionType -> {
                val s1 = Constraint(left.from, right.from).unify()
                val s2 = Constraint(left.to.substitute(s1), right.to.substitute(s1)).unify()
                return s1 + s2
            }
            left is PairType && right is PairType -> {
                val s1 = Constraint(left.left, right.left).unify()
                val s2 = Constraint(left.right.substitute(s1), right.right.substitute(s1)).unify()
                return s1 + s2
            }
            else -> throw UnificationException(this)
        }
    }

    override fun toString(): String {
        return "$left = $right"
    }
}