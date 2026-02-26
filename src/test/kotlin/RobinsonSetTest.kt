import dk.maxkandersen.unification.RobinsonSet
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.PairType
import dk.maxkandersen.type.TypeVar
import kotlin.test.*

class RobinsonSetTest {

    val a = TypeVar("a")
    val b = TypeVar("b")
    val c =  TypeVar("c")
    val d = TypeVar("d")
    val funab = FunctionType(a, b)
    val funba = FunctionType(b, a)
    val funcd = FunctionType(c, d)
    val pairab = PairType(a, b)
    val pairba = PairType(b, a)
    val paircd = PairType(c, d)

    @Test
    fun robinsonSetHasCorrectString() {
        val T = RobinsonSet(setOf(a, funab, pairab))
        assertEquals("{a, a -> b, a x b}", T.toString())
    }
}