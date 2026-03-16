import dk.maxkandersen.type.exceptions.InvalidWUnificationException
import dk.maxkandersen.unification.substitutionOf
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.IntType
import dk.maxkandersen.type.PairType
import dk.maxkandersen.type.TypeVar
import kotlin.test.*

class WUnificationTest {

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

    @BeforeTest
    fun setup() {
        TypeVar.reset()
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
        val s = a unify a
        assertTrue(s.isEmpty())
    }

    @Test
    fun typeVarsGiveCorrectSubstitution() {
        val s = a unify b
        assertEquals(b, s[a])
        assertEquals(1, s.size)
    }

    @Test
    fun typeVarAndArbitraryGiveCorrectSubstitution() {
        val s1 = a unify funcd
        assertEquals(funcd, s1[a])
        assertEquals(1, s1.size)
        val s2 = funcd unify a
        assertEquals(funcd, s2[a])
        assertEquals(1, s2.size)
    }

    @Test
    fun functionTypesGiveCorrectSubstitution() {
        val s = funab unify funcd
        assertEquals(c, s[a])
        assertEquals(d, s[b])
        assertEquals(2, s.size)
    }

    @Test
    fun functionTypesWithOverlapGivesCorrectSubstitution() {
        val s = funab unify funba
        assertEquals(b, s[a])
        assertEquals(1, s.size)
    }

    @Test
    fun pairTypesGiveCorrectSubstitution() {
        val s = pairab unify paircd
        assertEquals(c, s[a])
        assertEquals(d, s[b])
        assertEquals(2, s.size)
    }

    @Test
    fun pairTypesWithOverlapGivesCorrectSubstitution() {
        val s = pairab unify pairba
        assertEquals(b, s[a])
        assertEquals(1, s.size)
    }

    @Test
    fun unificationWithTypeVarInArbitraryFail() {
        assertFailsWith<InvalidWUnificationException> { a unify funab }
        assertFailsWith<InvalidWUnificationException> { a unify pairab }
    }

    @Test
    fun unificationOfIncompatibleTypesFail() {
        assertFailsWith<InvalidWUnificationException> { funab unify IntType }
        assertFailsWith<InvalidWUnificationException> { IntType unify pairab }
        assertFailsWith<InvalidWUnificationException> { funab unify pairab}
    }

}