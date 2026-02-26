package dk.maxkandersen.type

import dk.maxkandersen.unification.DisagreementPath

interface NaryType : Type {
    val subtypes: Array<Type>

    override fun includes(typeVar: TypeVar): Boolean {
        return subtypes.any { subtype -> subtype.includes(typeVar) }
    }

    override fun getSubPaths(): List<DisagreementPath> {
        return subtypes.withIndex().flatMap { (i, subtype) ->
            listOf(listOf(i)) + subtype.getSubPaths().map { subpath -> listOf(i) + subpath }
        }
    }

    override fun getSubtermAt(path: DisagreementPath): Type {
        return subtypes[path.first()].getSubtermAt(path.drop(1))
    }

}