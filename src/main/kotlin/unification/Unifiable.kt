package dk.maxkandersen.unification

interface Unifiable {
    fun unify(): Substitution
    override fun toString(): String
}