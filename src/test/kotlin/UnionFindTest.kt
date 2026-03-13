import dk.maxkandersen.type.TypeVar
import dk.maxkandersen.unification.unionfind.MapUnionFindForest
import kotlin.test.*

class UnionFindTest {

    @BeforeTest
    fun setup() {
        TypeVar.reset()
    }

    @Test
    fun mapUnionFindForestFindsCorrectlyInEmptyForest() {
        val uf = MapUnionFindForest()
        val a = TypeVar("a")
        val b = TypeVar("b")
        val t1 = uf.find(a)
        val t2 = uf.find(b)
        assertEquals(a, t1)
        assertEquals(b, t2)
    }

    @Test
    fun mapUnionFindForestUnifiesCorrectly() {
        val uf = MapUnionFindForest()
        val a = TypeVar("a")
        val b = TypeVar("b")
        uf.union(a, b)
        val t1 = uf.find(a)
        val t2 = uf.find(b)
        assertEquals(t1, t2)
    }

    @Test
    fun mapUnionFindForestUnifiesCorrectlyInTwoTrees() {
        val uf = MapUnionFindForest()
        val a = TypeVar("a")
        val b = TypeVar("b")
        val c = TypeVar("c")
        uf.union(a, b)
        uf.union(b, c)
        val t1 = uf.find(a)
        val t2 = uf.find(b)
        val t3 = uf.find(c)
        assertEquals(t1, t2)
        assertEquals(t2, t3)
    }

}