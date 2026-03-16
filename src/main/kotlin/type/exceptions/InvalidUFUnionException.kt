package dk.maxkandersen.type.exceptions

import dk.maxkandersen.type.Type

class InvalidUFUnionException(t1: Type, t2: Type) : RuntimeException("Invalid union: Could not union $t1 and $t2")