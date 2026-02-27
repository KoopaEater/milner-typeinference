package dk.maxkandersen.unification

class ConstraintUnificationException(constraint: Constraint) : RuntimeException("Unification error: Could not unify $constraint")
