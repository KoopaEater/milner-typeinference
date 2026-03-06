package dk.maxkandersen.expression

import dk.maxkandersen.type.Type
import dk.maxkandersen.unification.Substitution

typealias InferWResult = Pair<Substitution, Type>
val InferWResult.substitution : Substitution get() = first
val InferWResult.type : Type get() = second