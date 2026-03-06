package dk.maxkandersen.type

import dk.maxkandersen.unification.Substitution

interface TypeScheme {
    val quantifiers: List<TypeVar>
    val type: Type
    override fun toString(): String
    fun  substitute(substitution: Substitution): TypeScheme
    fun freeVars(): Set<TypeVar>
    fun alphaConvert(conversion: Map<TypeVar, TypeVar>): TypeScheme
    fun instantiate(): Type
}