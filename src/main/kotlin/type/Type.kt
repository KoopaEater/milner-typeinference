package dk.maxkandersen.type

import dk.maxkandersen.constraint.Substitution

interface Type : TypeScheme {
    fun substitute(substitution: Substitution): Type
    fun includes(typeVar: TypeVar): Boolean
}