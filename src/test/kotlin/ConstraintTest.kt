import dk.maxkandersen.constraint.Constraint
import dk.maxkandersen.constraint.UnificationException
import dk.maxkandersen.constraint.substitutionOf
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.IntType
import dk.maxkandersen.type.PairType
import dk.maxkandersen.type.TypeVar
import kotlin.test.*

class ConstraintTest {

    val a = TypeVar("a")
    val b = TypeVar("b")
    val c =  TypeVar("c")
    val d = TypeVar("d")
    val funab = FunctionType(a, b)
    val funba = FunctionType(b, a)
    val funcd = FunctionType(c, d)
    val pairab = PairType(a, b)
    val pairba = PairType(b, a)
    val paircd = PairType(c, d)

    @Test
    fun constraintHasCorrectString() {
        val constraint = Constraint(c, funab)
        assertEquals("c = a -> b", constraint.toString())
    }

    @Test
    fun equalConstraintsAreEqual() {
        val constraint1 = Constraint(c, funab)
        val constraint2 = Constraint(c, funab)
        assertEquals(constraint1, constraint2)
    }

    @Test
    fun unequalConstraintsAreNotEqual() {
        val constraint1 = Constraint(c, funab)
        val constraint2 = Constraint(a,b)
        assertNotEquals(constraint1, constraint2)
    }

    @Test
    fun typeVarsSubstituteCorrectly() {
        val s = substitutionOf(a to b)
        val a1 = a.substitute(s)
        assertEquals(b, a1)
        val b1 = b.substitute(s)
        assertEquals(b, b1)
    }

    @Test
    fun functionTypesSubstituteCorrectly() {
        val s1 = substitutionOf(a to c, b to d)
        val funab1 = funab.substitute(s1)
        assertEquals(funcd, funab1)
        val s2 = substitutionOf(a to c)
        val funcd1 = funcd.substitute(s2)
        assertEquals(funcd, funcd1)
    }

    @Test
    fun pairTypesSubstituteCorrectly() {
        val s1 = substitutionOf(a to c, b to d)
        val pairab1 = pairab.substitute(s1)
        assertEquals(paircd, pairab1)
        val s2 = substitutionOf(a to c)
        val paircd1 = paircd.substitute(s2)
        assertEquals(paircd, paircd1)
    }

    @Test
    fun equalTypesGiveEmptySubstitution() {
        val s = Constraint(a, a).unify()
        assertTrue(s.isEmpty())
    }

    @Test
    fun typeVarsGiveCorrectSubstitution() {
        val s = Constraint(a, b).unify()
        assertEquals(b, s[a])
        assertEquals(1, s.size)
    }

    @Test
    fun typeVarAndArbitraryGiveCorrectSubstitution() {
        val s1 = Constraint(a, funcd).unify()
        assertEquals(funcd, s1[a])
        assertEquals(1, s1.size)
        val s2 = Constraint(funcd, a).unify()
        assertEquals(funcd, s2[a])
        assertEquals(1, s2.size)
    }

    @Test
    fun functionTypesGiveCorrectSubstitution() {
        val s = Constraint(funab, funcd).unify()
        assertEquals(c, s[a])
        assertEquals(d, s[b])
        assertEquals(2, s.size)
    }

    @Test
    fun functionTypesWithOverlapGivesCorrectSubstitution() {
        val s = Constraint(funab, funba).unify()
        assertEquals(b, s[a])
        assertEquals(1, s.size)
    }

    @Test
    fun pairTypesGiveCorrectSubstitution() {
        val s = Constraint(pairab, paircd).unify()
        assertEquals(c, s[a])
        assertEquals(d, s[b])
        assertEquals(2, s.size)
    }

    @Test
    fun pairTypesWithOverlapGivesCorrectSubstitution() {
        val s = Constraint(pairab, pairba).unify()
        assertEquals(b, s[a])
        assertEquals(1, s.size)
    }

    @Test
    fun unificationWithTypeVarInArbitraryFail() {
        val constraint1 = Constraint(a, funab)
        val constraint2 = Constraint(a, pairab)
        assertFailsWith<UnificationException> { constraint1.unify() }
        assertFailsWith<UnificationException> { constraint2.unify() }
    }

    @Test
    fun unificationOfIncompatibleTypesFail() {
        val constraint1 = Constraint(funab, IntType)
        val constraint2 = Constraint(IntType, pairab)
        val constraint3 = Constraint(funab, pairab)
        assertFailsWith<UnificationException> { constraint1.unify() }
        assertFailsWith<UnificationException> { constraint2.unify() }
        assertFailsWith<UnificationException> { constraint3.unify() }
    }

}