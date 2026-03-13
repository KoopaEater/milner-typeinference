package dk.maxkandersen.unification.robinson

import dk.maxkandersen.type.Type

class RobinsonUnificationException(s: Type, t: Type) : RuntimeException("Unification error: Could not unify $s and $t")