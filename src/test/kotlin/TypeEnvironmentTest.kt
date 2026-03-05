import dk.maxkandersen.environment.substitute
import dk.maxkandersen.environment.typeEnvironmentOf
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.TypeVar
import dk.maxkandersen.unification.substitutionOf
import kotlin.test.*

class TypeEnvironmentTest {
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
}