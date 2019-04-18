package lorikeet;


import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class SeqTest {


    @Test
    public void testUniqueAllUnique() {
        Seq<Integer> ints = Seq.unique(Arrays.asList(1, 2, 3, 4));
        assertThat(ints).containsOnly(1, 2, 3, 4);
    }

    @Test
    public void testUniqueRemovesDuplicate() {
        Seq<Integer> ints = Seq.unique(Arrays.asList(1, 2, 3, 4, 3, 5, 5));
        assertThat(ints).containsOnly(1, 2, 3, 4, 5);
    }

    @Test
    public void testUniqueAllUniqueWithMapper() {
        Seq<String> nums = Seq.unique(Arrays.asList(1, 2, 3, 4), String::valueOf);
        assertThat(nums).containsOnly("1", "2", "3", "4");
    }

    @Test
    public void testUniqueRemovesDuplicateWithMapper() {
        Seq<String> nums = Seq.unique(Arrays.asList(1, 2, 3, 4, 3,5, 5), String::valueOf);
        assertThat(nums).containsOnly("1", "2", "3", "4", "5");
    }


}