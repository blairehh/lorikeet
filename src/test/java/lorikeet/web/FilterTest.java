package lorikeet.web;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FilterTest {

    @Test
    public void testRankIsHigher() {
        assertThat(filter(3)).isGreaterThan(filter(6));
    }

    @Test
    public void testRankIsLower() {
        assertThat(filter(6)).isLessThan(filter(3));
    }

    @Test
    public void testRankIsHigherThanNoRank() {
        assertThat(filter(6)).isGreaterThan(filter(-1));
    }

    @Test
    public void testNoRankIsAlwaysLowerThanARank() {
        assertThat(filter(-1)).isLessThan(filter(3));
    }


    static Filter filter(int rank) {
        return new Filter(HttpMethod.GET, "", rank);
    }

}