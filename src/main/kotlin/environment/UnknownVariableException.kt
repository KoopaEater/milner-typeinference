package dk.maxkandersen.environment

class UnknownVariableException(sym: Var) : RuntimeException("Unknown variable: $sym")