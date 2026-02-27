package dk.maxkandersen.type

import dk.maxkandersen.unification.DisagreementPath
import dk.maxkandersen.unification.Substitution

interface Type : TypeScheme, Comparable<Type> {
    fun substitute(substitution: Substitution): Type
    fun includes(typeVar: TypeVar): Boolean

    // TERM
    fun toTermString(): String
    fun getSubPaths(): List<DisagreementPath>
    fun getSubtermAt(path: DisagreementPath): Type
    fun hasSameTopSymbolAs(other: Type): Boolean
}