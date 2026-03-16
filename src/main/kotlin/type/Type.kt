package dk.maxkandersen.type

import dk.maxkandersen.unification.robinson.DisagreementPath
import dk.maxkandersen.unification.Substitution
import dk.maxkandersen.unification.unionfind.InvalidUnionException

interface Type : TypeScheme, Comparable<Type> {
    override val quantifiers: List<TypeVar>
        get() = emptyList()
    override val type: Type
        get() = this
    override fun alphaConvert(conversion: Map<TypeVar, TypeVar>): TypeScheme = this
    override fun instantiate(): Type = this

    override fun substitute(substitution: Substitution): Type
    fun includes(typeVar: TypeVar): Boolean

    //////// UNION-FIND ////////
    fun baseType(): Type
    infix fun union(other: Type) {
        val left = baseType()
        val right = other.baseType()
        when {
            left == right -> return
            left is TypeVar && !(right.includes(left)) -> left.ufParent = right
            right is TypeVar && !(left.includes(right)) -> right.ufParent = left
            left is FunctionType && right is FunctionType -> {
                left.from union right.from
                left.to union right.to
            }
            left is PairType && right is PairType -> {
                left.left union right.left
                left.right union right.right
            }
            else -> throw InvalidUnionException(this, other)
        }
    }

    //////// ROBINSON UNIFICATION ////////
    fun toTermString(): String
    fun getSubPaths(): List<DisagreementPath>
    fun getSubtermAt(path: DisagreementPath): Type
    fun hasSameTopSymbolAs(other: Type): Boolean
}