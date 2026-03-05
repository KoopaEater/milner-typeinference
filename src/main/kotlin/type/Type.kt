package dk.maxkandersen.type

import dk.maxkandersen.unification.DisagreementPath
import dk.maxkandersen.unification.Substitution

interface Type : TypeScheme, Comparable<Type> {
    override val quantifiers: List<TypeVar>
        get() = emptyList()
    override val type: Type
        get() = this
    override fun alphaConvert(conversion: Map<TypeVar, TypeVar>): TypeScheme = this

    override fun substitute(substitution: Substitution): Type
    fun includes(typeVar: TypeVar): Boolean

    //////// ROBINSON UNIFICATION ////////
    fun toTermString(): String
    fun getSubPaths(): List<DisagreementPath>
    fun getSubtermAt(path: DisagreementPath): Type
    fun hasSameTopSymbolAs(other: Type): Boolean
}