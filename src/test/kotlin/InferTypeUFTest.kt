import dk.maxkandersen.environment.UnknownVariableException
import dk.maxkandersen.environment.emptyTypeEnvironment
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
import dk.maxkandersen.type.BoolType
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.IntType
import dk.maxkandersen.type.PairType
import dk.maxkandersen.type.QuantifyingTypeScheme
import dk.maxkandersen.type.TypeVar
import dk.maxkandersen.type.exceptions.InvalidUFUnionException
import kotlin.test.*

class InferTypeUFTest {

    @BeforeTest
    fun setup() {
        TypeVar.reset()
    }

    @Test
    fun simpleVarExpInfersCorrectly() {
        val te = typeEnvironmentOf("x" to IntType)
        val exp = VarExp("x")
        val res = exp.inferTypeUF(te)
        assertEquals(IntType, res)
    }

    @Test
    fun complicatedVarExpInfersCorrectly() {
        val x = TypeVar("x")
        val y = TypeVar("y")
        val typescheme = QuantifyingTypeScheme(listOf(x, y), FunctionType(x, y))
        val te = typeEnvironmentOf("x" to typescheme)
        val exp = VarExp("x")
        val res = exp.inferTypeUF(te)
        val fresh0 = TypeVar("'t0")
        val fresh1 = TypeVar("'t1")
        val expected = FunctionType(fresh0, fresh1).baseType()
        assertEquals(expected, res)
    }

    @Test
    fun unknownVarExpFails() {
        val te = typeEnvironmentOf("x" to IntType)
        val exp = VarExp("y")
        assertFailsWith<UnknownVariableException> { exp.inferTypeUF(te) }
    }

    @Test
    fun lambdaExpInfersCorrectly() {
        val exp = LambdaExp("x", VarExp("x"))
        val res = exp.inferTypeUF(emptyTypeEnvironment())
        val fresh0 = TypeVar("'t0")
        val expected = FunctionType(fresh0, fresh0).baseType()
        assertEquals(expected, res)
    }

    @Test
    fun lambdaExpInfersCorrectly2() {
        val te = typeEnvironmentOf("y" to IntType)
        val exp = LambdaExp("x", VarExp("y"))
        val res = exp.inferTypeUF(te)
        val fresh0 = TypeVar("'t0")
        val expected = FunctionType(fresh0, IntType).baseType()
        assertEquals(expected, res)
    }

    @Test
    fun pairExpInfersCorrectly() {
        val a = TypeVar("a")
        val b = TypeVar("b")
        val te = typeEnvironmentOf("x" to a, "y" to b)
        val exp = PairExp(VarExp("x"), VarExp("y"))
        val res = exp.inferTypeUF(te)
        val expected = PairType(a, b).baseType()
        assertEquals(expected, res)
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
        val res = exp.inferTypeUF(te)
        val expected = a.baseType()
        assertEquals(expected, res)
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
        val res = exp.inferTypeUF(te)
        val expected = b.baseType()
        assertEquals(expected, res)
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
        val res = exp.inferTypeUF(te)
        val expected = b.baseType()
        assertEquals(expected, res)
    }

    @Test
    fun letExpInfersCorrectly() {
        val b = TypeVar("b")
        val x = VarExp("x")
        val y = VarExp("y")
        val exp = LetExp("x", y, x)
        val te = typeEnvironmentOf("y" to b)
        val res = exp.inferTypeUF(te)
        val expected = b.baseType()
        assertEquals(expected, res)
    }

    @Test
    fun intExpInfersCorrectly() {
        val exp = IntExp(1)
        val res = exp.inferTypeUF(emptyTypeEnvironment())
        assertEquals(IntType, res)
    }

    @Test
    fun boolExpInfersCorrectly() {
        val exp = BoolExp(true)
        val res = exp.inferTypeUF(emptyTypeEnvironment())
        assertEquals(BoolType, res)
    }

    // let f = λx.x in f 5 : Int
    @Test
    fun example1InfersCorrectly() {
        val te = emptyTypeEnvironment()
        val exp = LetExp("f", LambdaExp("x", VarExp("x")), ApplicationExp(VarExp("f"), IntExp(5)))
        val res = exp.inferTypeUF(te)
        assertEquals(IntType, res)
    }

    // (λp. snd p)(5 × true) : Bool
    @Test
    fun example2InfersCorrectly() {
        val te = emptyTypeEnvironment()
        val exp = ApplicationExp(LambdaExp("p", SndExp(VarExp("p"))), PairExp(IntExp(5), BoolExp(true)))
        val res = exp.inferTypeUF(te)
        assertEquals(BoolType, res)
    }

    // let f = λx.x in let g = λy.f y in g 5 : Int
    @Test
    fun example3InfersCorrectly() {
        val te = emptyTypeEnvironment()
        val exp = LetExp("f", LambdaExp("x", VarExp("x")), LetExp("g", LambdaExp("y", ApplicationExp(VarExp("f"), VarExp("y"))), ApplicationExp(VarExp("g"), IntExp(5))))
        val res = exp.inferTypeUF(te)
        assertEquals(IntType, res)
    }

    // let g = λx.x in let f = λx.λy.x y in f g 2 : Int
    @Test
    fun felixExampleInfersCorrectly() {
        val te = emptyTypeEnvironment()
        val exp = LetExp("g", LambdaExp("x", VarExp("x")), LetExp("f", LambdaExp("x", LambdaExp("y", ApplicationExp(VarExp("x"), VarExp("y")))), ApplicationExp(ApplicationExp(VarExp("f"), VarExp("g")), IntExp(2))))
        val res = exp.inferTypeUF(te)
        assertEquals(IntType, res)
    }

    // let f = λx.y in f 2 : t1
    @Test
    fun example4InfersCorrectly() {
        val t1 = TypeVar("t1")
        val te = typeEnvironmentOf("y" to t1)
        val exp = LetExp("f", LambdaExp("x", VarExp("y")), ApplicationExp(VarExp("f"), IntExp(2)))
        val res = exp.inferTypeUF(te)
        val expected = t1.baseType()
        assertEquals(expected, res)
    }

    // λf.(f 5, f true) : UnificationError
    @Test
    fun example5Fails() {
        val te = emptyTypeEnvironment()
        val exp = LambdaExp("f", PairExp(ApplicationExp(VarExp("f"), IntExp(5)), ApplicationExp(VarExp("f"), BoolExp(true))))
        assertFailsWith<InvalidUFUnionException> { exp.inferTypeUF(te) }
    }

    // let f = λx.e in (f 5, f true) : (t, t)
    @Test
    fun example6InfersCorrectly() {
        val t = TypeVar("t")
        val te = typeEnvironmentOf("e" to t)
        val exp = LetExp("f", LambdaExp("x", VarExp("e")), PairExp(ApplicationExp(VarExp("f"), IntExp(5)), ApplicationExp(VarExp("f"), BoolExp(true))))
        val res = exp.inferTypeUF(te)
        val expected = PairType(t,t).baseType()
        assertEquals(expected, res)
    }

    // let f = λx.x in (f 5, f true) : (Int, Bool)
    @Test
    fun example7InfersCorrectly() {
        val te = emptyTypeEnvironment()
        val exp = LetExp("f", LambdaExp("x", VarExp("x")), PairExp(ApplicationExp(VarExp("f"), IntExp(5)), ApplicationExp(VarExp("f"), BoolExp(true))))
        val res = exp.inferTypeUF(te)
        val expectedPair = PairType(IntType, BoolType)
        assertEquals(expectedPair, res)
    }
}