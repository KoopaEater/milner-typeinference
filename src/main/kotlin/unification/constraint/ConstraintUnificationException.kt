package dk.maxkandersen.unification.constraint

class ConstraintUnificationException(constraint: Constraint) : RuntimeException("Unification error: Could not unify $constraint")
