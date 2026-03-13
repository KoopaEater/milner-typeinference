package dk.maxkandersen.unification.unionfind

import dk.maxkandersen.type.Type

class MapUnionFindForest : UnionFindForest<Type> {

    val forest = mutableMapOf<Type, Type?>()

    override fun find(t: Type): Type {
        ensureElement(t)
        val parent = forest[t] ?: return t
        forest[t] = find(parent)
        return forest[t]!!
    }

    override fun union(t1: Type, t2: Type) {
        ensureElement(t1)
        ensureElement(t2)
        val parent1 = find(t1)
        val parent2 = find(t2)
        forest[parent1] = parent2
    }

    private fun ensureElement(t: Type) {
        if (t !in forest) forest[t] = null
    }
}