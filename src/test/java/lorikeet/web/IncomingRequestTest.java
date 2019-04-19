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
        assertThat(request.getPathVariable("id").get()).isEqualTo("1");
    }

    @Test
    public void testGetMissingPathVariable() {
        IncomingRequest request = request(Dict.of("id", "1"));
        assertThat(request.getPathVariable("num").isPresent()).isFalse();
    }

    @Test
    public void testGetNumPathVariable() {
        IncomingRequest request = request(Dict.of("id", "1"));
        assertThat(request.getNumberPathVariable("id").get().intValue()).isEqualTo(1);
    }

    private static IncomingRequest request(Dict<String, String> varibales) {
        return new StandardIncomingRequest(URI.create("/"), HttpMethod.GET, HttpHeaders.none(), varibales);
    }
}