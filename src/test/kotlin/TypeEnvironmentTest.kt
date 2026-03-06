import dk.maxkandersen.environment.closure
import dk.maxkandersen.environment.emptyTypeEnvironment
import dk.maxkandersen.environment.freeVars
import dk.maxkandersen.environment.substitute
import dk.maxkandersen.environment.typeEnvironmentOf
import dk.maxkandersen.type.BoolType
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.IntType
import dk.maxkandersen.type.PairType
import dk.maxkandersen.type.QuantifyingTypeScheme
import dk.maxkandersen.type.TypeVar
import dk.maxkandersen.unification.substitutionOf
import kotlin.test.*

class TypeEnvironmentTest {

    @BeforeTest
    fun setup() {
        TypeVar.reset()
    }

    @Test
    fun typeEnvironmentSubstitutesCorrectly() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val c = TypeVar("c")
        val funab = FunctionType(a, b)
        val funcb = FunctionType(c, b)
        val te = typeEnvironmentOf("x" to a, "y" to b, "z" to funab)
        val substitution = substitutionOf(a to c)
        val substituted = te.substitute(substitution)
        val expected = typeEnvironmentOf("x" to c, "y" to b, "z" to funcb)
        assertEquals(expected, substituted)
    }

    @Test
    fun typeEnvironmentHasCorrectFreeVars() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val c = TypeVar("c")
        val funab = FunctionType(a, b)
        val te = typeEnvironmentOf("x" to a, "y" to funab, "z" to c)
        val free = te.freeVars()
        assertEquals(setOf(a, b, c), free)
    }


    @Test
    fun boolTypeGivesCorrectClosure() {
        val te = emptyTypeEnvironment()
        val closure = te.closure(BoolType)
        val expected = QuantifyingTypeScheme(emptyList(), BoolType)
        assertEquals(expected, closure)
    }

    @Test
    fun intTypeGivesCorrectClosure() {
        val te = emptyTypeEnvironment()
        val closure = te.closure(IntType)
        val expected = QuantifyingTypeScheme(emptyList(), IntType)
        assertEquals(expected, closure)
    }

    @Test
    fun typeVarGivesCorrectClosure() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val te = typeEnvironmentOf("x" to b)
        val aClosure = te.closure(a)
        val bClosure = te.closure(b)
        val aExpected = QuantifyingTypeScheme(listOf(a), a)
        val bExpected = QuantifyingTypeScheme(emptyList(), b)
        assertEquals(aExpected, aClosure)
        assertEquals(bExpected, bClosure)
    }

    @Test
    fun functionTypeGivesCorrectClosure() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val c = TypeVar("c")
        val funab = FunctionType(a, b)
        val funac = FunctionType(a, c)
        val te = typeEnvironmentOf("x" to c)
        val funabClosure = te.closure(funab)
        val funacClosure = te.closure(funac)
        val funabExpected = QuantifyingTypeScheme(listOf(a, b), funab)
        val funacExpected = QuantifyingTypeScheme(listOf(a), funac)
        assertEquals(funabExpected, funabClosure)
        assertEquals(funacExpected, funacClosure)
    }

    @Test
    fun pairTypeGivesCorrectClosure() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val c = TypeVar("c")
        val pairab = PairType(a, b)
        val pairac = PairType(a, c)
        val te = typeEnvironmentOf("x" to c)
        val pairabClosure = te.closure(pairab)
        val pairacClosure = te.closure(pairac)
        val pairabExpected = QuantifyingTypeScheme(listOf(a, b), pairab)
        val pairacExpected = QuantifyingTypeScheme(listOf(a), pairac)
        assertEquals(pairabExpected, pairabClosure)
        assertEquals(pairacExpected, pairacClosure)
    }

}