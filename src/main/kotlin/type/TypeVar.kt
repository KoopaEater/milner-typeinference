package dk.maxkandersen.type

import dk.maxkandersen.constraint.Substitution

data class TypeVar(
    val sym: String
) : Type {
    constructor() : this("'${fresh()}")

    companion object {
        private var counter = 0
        private fun fresh(): String {
            val sym = "t$counter"
            counter++
            return sym
        }
    }

    override fun toString(): String {
        return sym
    }

    override fun substitute(substitution: Substitution): Type {
        return substitution[this] ?: return this
    }

    override fun includes(typeVar: TypeVar): Boolean {
        return this == typeVar
    }
}