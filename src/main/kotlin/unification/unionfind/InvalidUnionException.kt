package dk.maxkandersen.unification.unionfind

import dk.maxkandersen.type.Type

class InvalidUnionException(t1: Type, t2: Type) : RuntimeException("Invalid union: Could not union $t1 and $t2")