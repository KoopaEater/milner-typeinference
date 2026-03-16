package dk.maxkandersen.type

import dk.maxkandersen.unification.Substitution
import dk.maxkandersen.unification.unionfind.InvalidUnionException

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

    //////// UNION-FIND ////////

    override fun baseType(): Type {
        return this
    }
}