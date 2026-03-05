package dk.maxkandersen.type

class InvalidAlphaConversionException(from: TypeVar, to: TypeVar, typeScheme: TypeScheme) : RuntimeException("Invalid alpha conversion: $from -> $to in $typeScheme")