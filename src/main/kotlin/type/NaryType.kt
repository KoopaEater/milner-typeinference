package dk.maxkandersen.type

import dk.maxkandersen.unification.DisagreementPath

interface NaryType : Type {
    val subtypes: Array<Type>

    override fun includes(typeVar: TypeVar): Boolean {
        return subtypes.any { subtype -> subtype.includes(typeVar) }
    }

    override fun getSubPaths(): List<DisagreementPath> {
        return listOf<DisagreementPath>(emptyList()) + subtypes.withIndex().flatMap { (i, subtype) ->
            subtype.getSubPaths().map { subpath -> listOf(i) + subpath }
        }
    }

    override fun getSubtermAt(path: DisagreementPath): Type {
        if (path.isEmpty()) return this
        return subtypes[path.first()].getSubtermAt(path.drop(1))
    }

}