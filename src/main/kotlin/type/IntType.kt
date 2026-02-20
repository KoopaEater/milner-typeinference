package dk.maxkandersen.type

import dk.maxkandersen.constraint.Substitution

object IntType : Type {
    override fun toString(): String {
        return "int"
    }

    override fun substitute(substitution: Substitution): Type {
        return IntType
    }

    override fun includes(typeVar: TypeVar): Boolean {
        return false
    }
}