package dk.maxkandersen.type

import dk.maxkandersen.unification.Substitution

interface ConstantType : Type {
    override fun substitute(substitution: Substitution): Type {
        return this
    }

    override fun freeVars(): Set<TypeVar> {
        return emptySet()
    }

    override fun includes(typeVar: TypeVar): Boolean {
        return false
    }

}