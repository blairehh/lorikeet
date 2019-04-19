package lorikeet.web;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IncomingRequestFilterTest {

    @Test
    public void test() {
        IncomingRequestFilter filter = new IncomingRequestFilter()
            .get("/yes")
            .post("/orders/{id}/end-customer");

        assertThat(filter.applicable(HttpMethod.GET, "/yes")).isTrue();
        assertThat(filter.applicable(HttpMethod.GET, "/no")).isFalse();
        assertThat(filter.applicable(HttpMethod.POST, "/yes")).isFalse();
        assertThat(filter.applicable(HttpMethod.POST, "/orders/5456/end-customer")).isTrue();
    }
}