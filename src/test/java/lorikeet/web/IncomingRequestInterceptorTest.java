package lorikeet.web;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IncomingRequestInterceptorTest {

    @Test
    public void testRankIsHigher() {
        assertThat(interceptor(3)).isGreaterThan(interceptor(6));
    }

    @Test
    public void testRankIsLower() {
        assertThat(interceptor(6)).isLessThan(interceptor(3));
    }

    @Test
    public void testRankIsHigherThanNoRank() {
        assertThat(interceptor(6)).isGreaterThan(interceptor(-1));
    }

    @Test
    public void testNoRankIsAlwaysLowerThanARank() {
        assertThat(interceptor(-1)).isLessThan(interceptor(3));
    }


    static IncomingRequestInterceptor interceptor(int rank) {
        return new IncomingRequestInterceptor() {
            @Override
            public void intercept(IncomingRequest request, OutgoingResponse response) {

            }

            @Override
            public int getRank() {
                return rank;
            }
        };
    }

}