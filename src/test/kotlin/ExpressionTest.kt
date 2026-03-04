import dk.maxkandersen.environment.typeEnvironmentOf
import dk.maxkandersen.expression.VarExp
import dk.maxkandersen.expression.substitution
import dk.maxkandersen.expression.type
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.IntType
import dk.maxkandersen.type.QuantifyingTypeScheme
import dk.maxkandersen.type.TypeVar
import dk.maxkandersen.unification.emptySubstitution
import dk.maxkandersen.unification.substitutionOf
import kotlin.test.*

class ExpressionTest {

    @Test
    fun simpleVarExpInfersCorrectly() {
        val te = typeEnvironmentOf("x" to IntType)
        val exp = VarExp("x")
        val res = exp.inferType(te)
        assertEquals(emptySubstitution(), res.substitution)
        assertEquals(IntType, res.type)
    }

    @Test
    fun complicatedVarExpInfersCorrectly() {
        val x = TypeVar("x")
        val y = TypeVar("y")
        val typescheme = QuantifyingTypeScheme(listOf(x, y), FunctionType(x, y))
        val te = typeEnvironmentOf("x" to typescheme)
        val exp = VarExp("x")
        val res = exp.inferType(te)
        assertEquals(emptySubstitution(), res.substitution)
        val fresh0 = TypeVar("'t0")
        val fresh1 = TypeVar("'t1")
        val freshFuntype = FunctionType(fresh0, fresh1)
        assertEquals(freshFuntype, res.type)
    }
}