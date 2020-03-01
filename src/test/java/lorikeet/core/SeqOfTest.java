package lorikeet.core;

import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SeqOfTest {

    @Test
    public void testEmpty() {
        assertThat(new SeqOf<String>()).isEmpty();
    }

    @Test
    public void testFromSingleValue() {
        assertThat(new SeqOf<>("a")).containsOnlyOnce("a");
    }

    @Test
    public void testFromList() {
        assertThat(new SeqOf<>(List.of("a", "b"))).containsOnlyOnce("a", "b");
    }

    @Test
    public void testFromFallible() {
        assertThat(new SeqOf<>(new Ok<>("a"))).containsOnlyOnce("a");
        assertThat(new SeqOf<>(new Err<>(new RuntimeException()))).isEmpty();
    }

    @Test
    public void testFromVarArgs() {
        assertThat(new SeqOf<>("a", "b", "c")).containsOnlyOnce("a", "b", "c");
    }

    @Test
    public void testCount() {
        assertThat(new SeqOf<>(1, 5, 10, 15, 20, 25).count((e) -> e < 12)).isEqualTo(3);
    }

    @Test
    public void testAdd() {
        assertThat(new SeqOf<>(1, 2, 3).affix(4)).containsExactly(1, 2, 3, 4);
    }

    @Test
    public void testAddMany() {
        assertThat(new SeqOf<>(1, 2, 3).affix(List.of(4, 5, 6))).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    public void testPickPresent() {
        assertThat(new SeqOf<>(1, 2, 3).pick(1)).hasValue(2);
    }

    @Test
    public void testPickNotPresent() {
        assertThat(new SeqOf<>(1, 2, 3).pick(5)).isEmpty();
    }

    @Test
    public void testPickWithPredicate() {
        assertThat(new SeqOf<>(1, 2, 3, 4, 5, 6).pick((e) -> e > 3)).containsExactly(4, 5, 6);
    }

    @Test
    public void testDropIndexPresent() {
        assertThat(new SeqOf<>("1", "2", "3").drop(0)).containsExactly("2", "3");
    }

    @Test
    public void testDropIndexNotPresent() {
        assertThat(new SeqOf<>("1", "2", "3", "4").drop(10)).containsExactly("1", "2", "3", "4");
    }

    @Test
    public void testDropElementPresent() {
        assertThat(new SeqOf<>("1", "2", "3").drop("3")).containsExactly("1", "2");
    }

    @Test
    public void testDropElementNotPresent() {
        assertThat(new SeqOf<>("1", "2", "3", "4").drop("10")).containsExactly("1", "2", "3", "4");
    }

    @Test
    public void testDropMany() {
        assertThat(new SeqOf<>(1, 2, 3, 4).drop(List.of(2, 4, 5))).containsExactly(1, 3);
    }

    @Test
    public void testDropWithPredicate() {
        assertThat(new SeqOf<>(1, 2, 3, 4, 5, 6).drop((e) -> e > 3)).containsExactly(1, 2, 3);
    }

    @Test
    public void testTakeIndexPresent() {
        Take<Integer, Optional<Integer>> take = new SeqOf<>(1, 2, 3, 4).take(1);
        assertThat(take.element()).hasValue(2);
        assertThat(take.seq()).containsExactly(1, 3, 4);
    }

    @Test
    public void testTakeNotIndexPresent() {
        Take<Integer, Optional<Integer>> take = new SeqOf<>(1, 2, 3, 4).take(10);
        assertThat(take.element()).isEmpty();
        assertThat(take.seq()).containsExactly(1, 2, 3, 4);
    }

    @Test
    public void testTakePredicate() {
        Take<Integer, Seq<Integer>> take = new SeqOf<>(1, 2, 3, 4).take((n) -> n == 2);
        assertThat(take.element()).containsExactly(2);
        assertThat(take.seq()).containsExactly(1, 3, 4);
    }

    @Test
    public void testRemodel() {
        assertThat(new SeqOf<>(1, 2, 3).remodel(Object::toString)).containsExactly("1", "2", "3");
    }

    @Test
    public void testModify() {
        assertThat(new SeqOf<>(1, 2, 3).modify((x) -> x * 2)).containsExactly(2, 4, 6);
    }

    @Test
    public void testModifyWithPredicate() {
        assertThat(new SeqOf<>(1, 2, 3).modify((x) -> x % 2 == 0, (x) -> x * 2)).containsExactly(1, 4, 3);
    }

    @Test
    public void testForkMutable() {
        List<Integer> list = new SeqOf<>(1, 2, 3).forkMutable();
        list.add(4);
        assertThat(list).containsExactly(1, 2, 3, 4);
    }
}