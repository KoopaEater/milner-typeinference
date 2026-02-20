import dk.maxkandersen.type.BoolType
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.IntType
import dk.maxkandersen.type.PairType
import dk.maxkandersen.type.QuantifyingTypeScheme
import dk.maxkandersen.type.TypeVar
import kotlin.test.*

class TypeTest {

    @Test
    fun boolTypeHasCorrectString() {
        assertEquals("bool", BoolType.toString())
    }

    @Test
    fun intTypeHasCorrectString() {
        assertEquals("int", IntType.toString())
    }

    @Test
    fun typeVarHasCorrectString() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        assertEquals("a", a.toString())
        assertEquals("b", b.toString())
    }

    @Test
    fun freshTypeVarHasCorrectString() {
        val a = TypeVar()
        val b = TypeVar()
        assertNotEquals(a.toString(), b.toString())
    }

    @Test
    fun functionTypeHasCorrectString() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val funtyp = FunctionType(a, b)
        assertEquals("a -> b", funtyp.toString())
    }

    @Test
    fun pairTypeHasCorrectString() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val pair = PairType(a, b)
        assertEquals("a x b", pair.toString())
    }

    @Test
    fun quantifyingTypeSchemeHasCorrectString() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val typescheme = QuantifyingTypeScheme(b, FunctionType(a, b))
        assertEquals("\\/b.a -> b", typescheme.toString())
    }

    @Test
    fun equalTypeVarsAreEqual() {
        val a1 = TypeVar("a")
        val a2 = TypeVar("a")
        assertEquals(a1, a2)
    }

    @Test
    fun unequalTypeVarsAreNotEqual() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        assertNotEquals(a, b)
    }

    @Test
    fun freshTypeVarsAreNotEqual() {
        val a1 = TypeVar()
        val a2 = TypeVar()
        assertNotEquals(a1, a2)
    }

    @Test
    fun equalFunctionTypesAreEqual() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val funtyp1 = FunctionType(a, b)
        val funtyp2 = FunctionType(a, b)
        assertEquals(funtyp1, funtyp2)
    }

    @Test
    fun unequalFunctionTypesAreNotEqual() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val funtyp1 = FunctionType(a, b)
        val funtyp2 = FunctionType(b, a)
        assertNotEquals(funtyp1, funtyp2)
    }

    @Test
    fun equalPairTypesAreEqual() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val pair1 = PairType(a, b)
        val pair2 = PairType(a, b)
        assertEquals(pair1, pair2)
    }

    @Test
    fun unequalPairTypesAreNotEqual() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val pair1 = PairType(a, b)
        val pair2 = PairType(b, a)
        assertNotEquals(pair1, pair2)
    }

    @Test
    fun equalQuantifyingTypeSchemesAreEqual() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val typescheme1 = QuantifyingTypeScheme(b, FunctionType(a, b))
        val typescheme2 = QuantifyingTypeScheme(b, FunctionType(a, b))
        assertEquals(typescheme1, typescheme2)
    }

    @Test
    fun unequalQuantifyingTypeSchemesAreNotEqual() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val typescheme1 = QuantifyingTypeScheme(b, FunctionType(a, b))
        val typescheme2 = QuantifyingTypeScheme(a, FunctionType(b, a))
        assertNotEquals(typescheme1, typescheme2)
    }
}