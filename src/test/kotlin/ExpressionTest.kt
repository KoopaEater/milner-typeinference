import dk.maxkandersen.environment.TypeEnvironment
import dk.maxkandersen.environment.UnknownVariableException
import dk.maxkandersen.environment.emptyTypeEnvironment
import dk.maxkandersen.environment.substitute
import dk.maxkandersen.environment.typeEnvironmentOf
import dk.maxkandersen.expression.ApplicationExp
import dk.maxkandersen.expression.FstExp
import dk.maxkandersen.expression.LambdaExp
import dk.maxkandersen.expression.LetExp
import dk.maxkandersen.expression.PairExp
import dk.maxkandersen.expression.SndExp
import dk.maxkandersen.expression.VarExp
import dk.maxkandersen.expression.substitution
import dk.maxkandersen.expression.type
import dk.maxkandersen.type.BoolType
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.IntType
import dk.maxkandersen.type.PairType
import dk.maxkandersen.type.QuantifyingTypeScheme
import dk.maxkandersen.type.TypeVar
import dk.maxkandersen.unification.emptySubstitution
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

    @Test
    fun pairExpInfersCorrectly() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val te = typeEnvironmentOf("x" to a, "y" to b)
        val exp = PairExp(VarExp("x"), VarExp("y"))
        val res = exp.inferType(te)
        assertEquals(emptySubstitution(), res.substitution)
        val expectedType = PairType(a, b)
        assertEquals(expectedType, res.type)
    }

    @Test
    fun fstExpInfersCorrectly() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val x = VarExp("x")
        val y = VarExp("y")
        val pair = PairExp(x, y)
        val te = typeEnvironmentOf("x" to a, "y" to b)
        val exp = FstExp(pair)
        val res = exp.inferType(te)
        val expectedType = te.substitute(res.substitution)["x"]
        assertEquals(expectedType, res.type)
    }

    @Test
    fun sndExpInfersCorrectly() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val x = VarExp("x")
        val y = VarExp("y")
        val pair = PairExp(x, y)
        val te = typeEnvironmentOf("x" to a, "y" to b)
        val exp = SndExp(pair)
        val res = exp.inferType(te)
        val expectedType = te.substitute(res.substitution)["y"]
        assertEquals(expectedType, res.type)
    }

    @Test
    fun applicationExpInfersCorrectly() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val x = VarExp("x")
        val y = VarExp("y")
        val funxy = LambdaExp("x", y)
        val te = typeEnvironmentOf("x" to a, "y" to b)
        val exp = ApplicationExp(funxy, x)
        val res = exp.inferType(te)
        val expectedType = te.substitute(res.substitution)["y"]
        assertEquals(expectedType, res.type)
    }

    @Test
    fun letExpInfersCorrectly() {
        val b = TypeVar("b")
        val x = VarExp("x")
        val y = VarExp("y")
        val exp = LetExp("x", y, x)
        val te = typeEnvironmentOf("y" to b)
        val res = exp.inferType(te)
        val expectedType = te.substitute(res.substitution)["y"]
        assertEquals(expectedType, res.type)
    }

}