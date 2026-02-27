package dk.maxkandersen.unification

import dk.maxkandersen.type.Type

class RobinsonUnificationException(val s: Type, val t: Type) : RuntimeException("Unification error: Could not unify $s and $t")