package dk.maxkandersen.unification

class UnificationException(constraint: Constraint) : RuntimeException("Unification error: Could not unify $constraint")
