package dk.maxkandersen.type

import dk.maxkandersen.constraint.Substitution

object BoolType : Type {
    override fun toString(): String {
        return "bool"
    }

    override fun substitute(substitution: Substitution): Type {
        return BoolType
    }

    override fun includes(typeVar: TypeVar): Boolean {
        return false
    }
}