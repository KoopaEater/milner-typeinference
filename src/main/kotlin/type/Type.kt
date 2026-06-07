package dk.maxkandersen.type

import dk.maxkandersen.unification.robinson.DisagreementPath
import dk.maxkandersen.unification.Substitution
import dk.maxkandersen.type.exceptions.InvalidWUnificationException
import dk.maxkandersen.unification.emptySubstitution
import dk.maxkandersen.unification.substitutionOf
import dk.maxkandersen.type.exceptions.InvalidUFUnionException
import dk.maxkandersen.unification.compose

interface Type : TypeScheme, Comparable<Type> {
    override val quantifiers: List<TypeVar>
        get() = emptyList()
    override val type: Type
        get() = this
    override fun alphaConvert(conversion: Map<TypeVar, TypeVar>): TypeScheme = this
    override fun instantiate(): Type = this

    override fun substitute(substitution: Substitution): Type
    fun includes(typeVar: TypeVar): Boolean

    //////// W ////////

    infix fun unify(other: Type): Substitution {
        return when {
            this == other -> emptySubstitution()
            this is TypeVar && !(other.includes(this)) -> substitutionOf(this to other)
            other is TypeVar && !(this.includes(other)) -> substitutionOf(other to this)
            this is FunctionType && other is FunctionType -> {
                val s1 = this.from unify other.from
                val s2 = this.to.substitute(s1) unify other.to.substitute(s1)
                return s1 compose s2
            }
            this is PairType && other is PairType -> {
                val s1 = this.left unify other.left
                val s2 = this.right.substitute(s1) unify other.right.substitute(s1)
                return s1 compose s2
            }
            else -> throw InvalidWUnificationException(this, other)
        }
    }

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
            else -> throw InvalidUFUnionException(this, other)
        }
    }

    //////// ROBINSON UNIFICATION ////////
    fun toTermString(): String
    fun getSubPaths(): List<DisagreementPath>
    fun getSubtermAt(path: DisagreementPath): Type
    fun hasSameTopSymbolAs(other: Type): Boolean
}