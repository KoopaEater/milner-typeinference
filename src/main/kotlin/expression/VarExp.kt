package dk.maxkandersen.expression

import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.environment.UnknownVariableException
import dk.maxkandersen.environment.Var
import dk.maxkandersen.type.TypeVar
import dk.maxkandersen.unification.emptySubstitution
import dk.maxkandersen.unification.substitutionOf

data class VarExp(val sym: Var): Expression {
    override fun inferType(te: TypeEnvironment): InferResult {
        val envType = te[sym] ?: throw UnknownVariableException(sym)
        val quantifiers = envType.quantifiers
        val freshTypes = List(quantifiers.size) { TypeVar() }
        val pairs = quantifiers.zip(freshTypes).toTypedArray()
        val substitution = substitutionOf(*pairs)
        val newType = envType.type.substitute(substitution)
        return emptySubstitution() to newType
    }
}