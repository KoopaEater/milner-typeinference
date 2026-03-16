package dk.maxkandersen.type.exceptions

import dk.maxkandersen.type.TypeScheme
import dk.maxkandersen.type.TypeVar

class InvalidAlphaConversionException(from: TypeVar, to: TypeVar, typeScheme: TypeScheme) : RuntimeException("Invalid alpha conversion: $from -> $to in $typeScheme")