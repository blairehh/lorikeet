package lorikeet.web;

import lorikeet.Dict;
import lorikeet.web.impl.StandardIncomingRequest;
import org.junit.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class IncomingRequestTest {

    @Test
    public void testGetPresentPathVariable() {
        IncomingRequest request = request(Dict.of("id", "1"));
        assertThat(request.findPathVariable("id").get()).isEqualTo("1");
    }

    @Test
    public void testGetMissingPathVariable() {
        IncomingRequest request = request(Dict.of("id", "1"));
        assertThat(request.findPathVariable("num").isPresent()).isFalse();
    }

    @Test
    public void testGetNumPathVariable() {
        IncomingRequest request = request(Dict.of("id", "1"));
        assertThat(request.findNumberPathVariable("id").get().intValue()).isEqualTo(1);
    }

    private static IncomingRequest request(Dict<String, String> varibales) {
        return new StandardIncomingRequest(URI.create("/"), HttpMethod.GET, Dict.empty(), varibales);
    }
}