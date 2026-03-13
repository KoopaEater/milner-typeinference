package dk.maxkandersen.unification.unionfind

interface UnionFindForest<T> {
    fun find(t: T): T
    fun union(t1: T, t2: T)
}