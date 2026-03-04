package dk.maxkandersen.expression

import dk.maxkandersen.type.Type
import dk.maxkandersen.unification.Substitution

typealias InferResult = Pair<Substitution, Type>
val InferResult.substitution : Substitution get() = first
val InferResult.type : Type get() = second