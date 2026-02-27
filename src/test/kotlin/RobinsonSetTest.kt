import dk.maxkandersen.unification.RobinsonSet
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.IntType
import dk.maxkandersen.type.PairType
import dk.maxkandersen.type.TypeVar
import dk.maxkandersen.unification.RobinsonUnificationException
import kotlin.test.*

class RobinsonSetTest {

    val a = TypeVar("a")
    val b = TypeVar("b")
    val c =  TypeVar("c")
    val d = TypeVar("d")
    val funab = FunctionType(a, b)
    val funac = FunctionType(a, c)
    val funba = FunctionType(b, a)
    val funcd = FunctionType(c, d)
    val pairab = PairType(a, b)
    val pairba = PairType(b, a)
    val paircd = PairType(c, d)
    val mixed = FunctionType(FunctionType(a, PairType(b, c)), d)
    val crazyfun1 = FunctionType(FunctionType(a, b), FunctionType(b, c))
    val crazyfun2 = FunctionType(FunctionType(a, b), FunctionType(c, c))
    val crazyfun3 = FunctionType(FunctionType(a, b), FunctionType(d, c))

    @Test
    fun robinsonSetHasCorrectString() {
        val T = RobinsonSet(a, funab, pairab)
        assertEquals("{a, f(a,b), p(a,b)}", T.toString())
    }

    @Test
    fun getAllPathsGivesCorrectSetOfPaths() {
        val T1 = RobinsonSet(a)
        val T2 = RobinsonSet(a, funab, mixed)
        println(T1)
        println(T2)
        assertEquals(listOf(emptyList()), T1.getAllPaths())
        assertEquals(listOf(emptyList(), listOf(0), listOf(0, 0), listOf(0, 1), listOf(0, 1, 0), listOf(0, 1, 1), listOf(1)), T2.getAllPaths())
    }

    @Test
    fun getDisagreementSetGivesCorrectSet() {
        val T1 = RobinsonSet(a, b)
        val T2 = RobinsonSet(crazyfun1, crazyfun2, crazyfun3)
        val disagree1 = T1.getDisagreementPair()
        val disagree2 = T2.getDisagreementPair()
        assertTrue { disagree1.isPresent }
        assertTrue { disagree2.isPresent }
        assertEquals(a to b, disagree1.get())
        assertEquals(b to c, disagree2.get())
    }

    @Test
    fun equalTypesGiveEmptySubstitution() {
        val s = RobinsonSet(a, a).unify()
        assertTrue(s.isEmpty())
    }

    @Test
    fun typeVarsGiveCorrectSubstitution() {
        val s = RobinsonSet(a, b).unify()
        assertEquals(b, s[a])
        assertEquals(1, s.size)
    }

    @Test
    fun typeVarAndArbitraryGiveCorrectSubstitution() {
        val s1 = RobinsonSet(a, funcd).unify()
        assertEquals(funcd, s1[a])
        assertEquals(1, s1.size)
        val s2 = RobinsonSet(funcd, a).unify()
        assertEquals(funcd, s2[a])
        assertEquals(1, s2.size)
    }

    @Test
    fun functionTypesGiveCorrectSubstitution() {
        val s = RobinsonSet(funab, funcd).unify()
        assertEquals(c, s[a])
        assertEquals(d, s[b])
        assertEquals(2, s.size)
    }

    @Test
    fun functionTypesWithOverlapGivesCorrectSubstitution() {
        val s = RobinsonSet(funab, funba).unify()
        assertEquals(b, s[a])
        assertEquals(1, s.size)
    }

    @Test
    fun pairTypesGiveCorrectSubstitution() {
        val s = RobinsonSet(pairab, paircd).unify()
        assertEquals(c, s[a])
        assertEquals(d, s[b])
        assertEquals(2, s.size)
    }

    @Test
    fun pairTypesWithOverlapGivesCorrectSubstitution() {
        val s = RobinsonSet(pairab, pairba).unify()
        assertEquals(b, s[a])
        assertEquals(1, s.size)
    }

    @Test
    fun unificationWithTypeVarInArbitraryFail() {
        val T1 = RobinsonSet(a, funab)
        val T2 = RobinsonSet(a, pairab)
        assertFailsWith<RobinsonUnificationException> { T1.unify() }
        assertFailsWith<RobinsonUnificationException> { T2.unify() }
    }

    @Test
    fun unificationOfIncompatibleTypesFail() {
        val T1 = RobinsonSet(funab, IntType)
        val T2 = RobinsonSet(IntType, pairab)
        val T3 = RobinsonSet(funab, pairab)
        assertFailsWith<RobinsonUnificationException> { T1.unify() }
        assertFailsWith<RobinsonUnificationException> { T2.unify() }
        assertFailsWith<RobinsonUnificationException> { T3.unify() }
    }

    @Test
    fun multipleTypesGivesCorrectSubstitution() {
        val s = RobinsonSet(a, b, c).unify()
        assertEquals(c, s[a])
        assertEquals(c, s[b])
        assertEquals(2, s.size)
    }

    @Test
    fun mixedMultipleTypesGivesCorrectSubstitution() {
        val s = RobinsonSet(funab, c, d).unify()
        assertEquals(funab, s[c])
        assertEquals(funab, s[d])
        assertEquals(2, s.size)
    }

    @Test
    fun multipleFunctionTypesGivesCorrectSubstitution() {
        val s = RobinsonSet(funab, funac, funba).unify()
        assertEquals(c, s[a])
        assertEquals(c, s[b])
        assertEquals(2, s.size)
    }


}