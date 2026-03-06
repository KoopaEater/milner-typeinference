import dk.maxkandersen.environment.UnknownVariableException
import dk.maxkandersen.environment.emptyTypeEnvironment
import dk.maxkandersen.environment.substitute
import dk.maxkandersen.environment.typeEnvironmentOf
import dk.maxkandersen.expression.ApplicationExp
import dk.maxkandersen.expression.BoolExp
import dk.maxkandersen.expression.FstExp
import dk.maxkandersen.expression.IntExp
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
import dk.maxkandersen.unification.ConstraintUnificationException
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

    @Test
    fun intExpInfersCorrectly() {
        val exp = IntExp(1)
        val res = exp.inferType(emptyTypeEnvironment())
        assertEquals(emptySubstitution(), res.substitution)
        assertEquals(IntType, res.type)
    }

    @Test
    fun boolExpInfersCorrectly() {
        val exp = BoolExp(true)
        val res = exp.inferType(emptyTypeEnvironment())
        assertEquals(emptySubstitution(), res.substitution)
        assertEquals(BoolType, res.type)
    }

    // let f = λx.x in f 5 : Int
    @Test
    fun example1InfersCorrectly() {
        val te = emptyTypeEnvironment()
        val exp = LetExp("f", LambdaExp("x", VarExp("x")), ApplicationExp(VarExp("f"), IntExp(5)))
        val res = exp.inferType(te)
        assertEquals(IntType, res.type)
    }

    // (λp. snd p)(5 × true) : Bool
    @Test
    fun example2InfersCorrectly() {
        val te = emptyTypeEnvironment()
        val exp = ApplicationExp(LambdaExp("p", SndExp(VarExp("p"))), PairExp(IntExp(5), BoolExp(true)))
        val res = exp.inferType(te)
        assertEquals(BoolType, res.type)
    }

    // let f = λx.x in let g = λy.f y in g 5 : Int
    @Test
    fun example3InfersCorrectly() {
        val te = emptyTypeEnvironment()
        val exp = LetExp("f", LambdaExp("x", VarExp("x")), LetExp("g", LambdaExp("y", ApplicationExp(VarExp("f"), VarExp("y"))), ApplicationExp(VarExp("g"), IntExp(5))))
        val res = exp.inferType(te)
        assertEquals(IntType, res.type)
    }

    // let g = λx.x in let f = λx.λy.x y in f g 2 : Int
    @Test
    fun felixExampleInfersCorrectly() {
        val te = emptyTypeEnvironment()
        val exp = LetExp("g", LambdaExp("x", VarExp("x")), LetExp("f", LambdaExp("x", LambdaExp("y", ApplicationExp(VarExp("x"), VarExp("y")))), ApplicationExp(ApplicationExp(VarExp("f"), VarExp("g")), IntExp(2))))
        val res = exp.inferType(te)
        assertEquals(IntType, res.type)
    }

    // let f = λx.y in f 2 : t1
    @Test
    fun example4InfersCorrectly() {
        val te = typeEnvironmentOf("y" to TypeVar("t1"))
        val exp = LetExp("f", LambdaExp("x", VarExp("y")), ApplicationExp(VarExp("f"), IntExp(2)))
        val res = exp.inferType(te)
        val expectedType = te.substitute(res.substitution)["y"]
        assertEquals(expectedType, res.type)
    }

    // λf.(f 5, f true) : UnificationError
    @Test
    fun example5Fails() {
        val te = emptyTypeEnvironment()
        val exp = LambdaExp("f", PairExp(ApplicationExp(VarExp("f"), IntExp(5)), ApplicationExp(VarExp("f"), BoolExp(true))))
        assertFailsWith<ConstraintUnificationException> { exp.inferType(te) }
    }

    // let f = λx.e in (f 5, f true) : (t, t)
    @Test
    fun example6InfersCorrectly() {
        val te = typeEnvironmentOf("e" to TypeVar("t"))
        val exp = LetExp("f", LambdaExp("x", VarExp("e")), PairExp(ApplicationExp(VarExp("f"), IntExp(5)), ApplicationExp(VarExp("f"), BoolExp(true))))
        val res = exp.inferType(te)
        val substitutedType = te.substitute(res.substitution)["e"]!!.instantiate()
        val expectedPair = PairType(substitutedType, substitutedType)
        assertEquals(expectedPair, res.type)
    }

}