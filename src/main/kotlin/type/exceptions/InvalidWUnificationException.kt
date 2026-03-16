package dk.maxkandersen.type.exceptions

import dk.maxkandersen.type.Type

class InvalidWUnificationException(t1: Type, t2: Type) : RuntimeException("Invalid unification: Could not unify $t1 and $t2")
