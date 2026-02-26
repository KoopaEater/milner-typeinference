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
    fun boolTypeHasCorrectTermString() {
        assertEquals("bool", BoolType.toTermString())
    }

    @Test
    fun intTypeHasCorrectString() {
        assertEquals("int", IntType.toString())
    }

    @Test
    fun intTypeHasCorrectTermString() {
        assertEquals("int", IntType.toTermString())
    }

    @Test
    fun typeVarHasCorrectString() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        assertEquals("a", a.toString())
        assertEquals("b", b.toString())
    }

    @Test
    fun typeVarHasCorrectTermString() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        assertEquals("a", a.toTermString())
        assertEquals("b", b.toTermString())
    }

    @Test
    fun freshTypeVarHasCorrectString() {
        val a = TypeVar()
        val b = TypeVar()
        assertNotEquals(a.toString(), b.toString())
    }

    @Test
    fun freshTypeVarHasCorrectTermString() {
        val a = TypeVar()
        val b = TypeVar()
        assertNotEquals(a.toTermString(), b.toTermString())
    }

    @Test
    fun functionTypeHasCorrectString() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val funtyp = FunctionType(a, b)
        assertEquals("a -> b", funtyp.toString())
    }

    @Test
    fun functionTypeHasCorrectTermString() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val funtyp = FunctionType(a, b)
        assertEquals("f(a,b)", funtyp.toTermString())
    }

    @Test
    fun pairTypeHasCorrectString() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val pair = PairType(a, b)
        assertEquals("a x b", pair.toString())
    }

    @Test
    fun pairTypeHasCorrectTermString() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val pair = PairType(a, b)
        assertEquals("p(a,b)", pair.toTermString())
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

    @Test
    fun boolTypeHasCorrectSubPath() {
        val subpaths = BoolType.getSubPaths()
        assertTrue(subpaths.isEmpty())
    }

    @Test
    fun intTypeHasCorrectSubPath() {
        val subpaths = IntType.getSubPaths()
        assertTrue(subpaths.isEmpty())
    }

    @Test
    fun typeVarHasCorrectSubPath() {
        val a = TypeVar("a")
        val subpaths = a.getSubPaths()
        assertTrue(subpaths.isEmpty())
    }

    @Test
    fun funTypeHasCorrectSubPath() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val funtyp = FunctionType(a, b)
        val subpaths = funtyp.getSubPaths()
        assertEquals(listOf(listOf(0), listOf(1)), subpaths)
    }

    @Test
    fun nestedFunTypeHasCorrectSubPath() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val c = TypeVar("c")
        val funtyp = FunctionType(FunctionType(a, b), c)
        val subpaths = funtyp.getSubPaths()
        assertEquals(listOf(listOf(0), listOf(0, 0), listOf(0, 1), listOf(1)), subpaths)
    }

    @Test
    fun pairTypeHasCorrectSubPath() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val pair = PairType(a, b)
        val subpaths = pair.getSubPaths()
        assertEquals(listOf(listOf(0), listOf(1)), subpaths)
    }

    @Test
    fun nestedPairTypeHasCorrectSubPath() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val c = TypeVar("c")
        val pair = PairType(PairType(a, b), c)
        val subpaths = pair.getSubPaths()
        assertEquals(listOf(listOf(0), listOf(0, 0), listOf(0, 1), listOf(1)), subpaths)
    }

    @Test
    fun mixedNestedTypesHaveCorrectSubPath() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val c = TypeVar("c")
        val d = TypeVar("d")
        val funtyp = FunctionType(FunctionType(a, PairType(b, c)), d)
        val subpaths = funtyp.getSubPaths()
        assertEquals(listOf(listOf(0), listOf(0, 0), listOf(0, 1), listOf(0, 1, 0), listOf(0, 1, 1), listOf(1)), subpaths)
    }
}