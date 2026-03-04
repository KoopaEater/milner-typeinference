import dk.maxkandersen.environment.UnknownVariableException
import dk.maxkandersen.environment.emptyTypeEnvironment
import dk.maxkandersen.environment.typeEnvironmentOf
import dk.maxkandersen.expression.LambdaExp
import dk.maxkandersen.expression.VarExp
import dk.maxkandersen.expression.substitution
import dk.maxkandersen.expression.type
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.IntType
import dk.maxkandersen.type.QuantifyingTypeScheme
import dk.maxkandersen.type.TypeVar
import dk.maxkandersen.unification.emptySubstitution
import org.junit.jupiter.api.assertThrows
import kotlin.test.*

class ExpressionTest {

    @BeforeTest
    fun setup() {
        TypeVar.reset()
    }

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

    @Test
    fun unknownVarExpFails() {
        val te = typeEnvironmentOf("x" to IntType)
        val exp = VarExp("y")
        assertFailsWith<UnknownVariableException> { exp.inferType(te) }
    }

    @Test
    fun lambdaExpInfersCorrectly() {
        val exp = LambdaExp("x", VarExp("x"))
        val res = exp.inferType(emptyTypeEnvironment())
        assertEquals(emptySubstitution(), res.substitution)
        val fresh0 = TypeVar("'t0")
        val freshFuntype = FunctionType(fresh0, fresh0)
        assertEquals(freshFuntype, res.type)
    }

    @Test
    fun lambdaExpInfersCorrectly2() {
        val te = typeEnvironmentOf("y" to IntType)
        val exp = LambdaExp("x", VarExp("y"))
        val res = exp.inferType(te)
        assertEquals(emptySubstitution(), res.substitution)
        val fresh0 = TypeVar("'t0")
        val expectedType = FunctionType(fresh0, IntType)
        assertEquals(expectedType, res.type)
    }

}