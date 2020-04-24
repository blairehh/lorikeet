package lorikeet.http;

import lorikeet.TestResources;
import lorikeet.core.FallibleResult;
import lorikeet.core.StringInputStream;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.error.UnableToDecodeHttpRequestBody;
import lorikeet.http.msg.SampleJson;
import lorikeet.lobe.DefaultTract;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonBodyTest {

    private JsonBody<SampleJson, TestResources> jsonBody = new JsonBody<>(SampleJson.class);

    @Test
    public void testValid() {
        MockIncomingHttpSgnl sgnl = new MockIncomingHttpSgnl(
            "/test",
            new StringInputStream("{\"id\": 1, \"name\": \"Bob\", \"active\": true}")
        );
        FallibleResult<SampleJson, IncomingHttpSgnlError> result = jsonBody.include(
            sgnl,
            new DefaultTract<>(new TestResources())
        );

        assertThat(result.success()).isTrue();
        SampleJson json = result.orPanic();
        assertThat(json.id).isEqualTo(1);
        assertThat(json.name).isEqualTo("Bob");
        assertThat(json.active).isTrue();
    }

    @Test
    public void testInvalidJson() {
        MockIncomingHttpSgnl sgnl = new MockIncomingHttpSgnl(
            "/test",
            new StringInputStream("{\"id\": \"Bob\", \"name\": \"Bob\", \"active\": false}")
        );
        FallibleResult<SampleJson, IncomingHttpSgnlError> result = jsonBody.include(
            sgnl,
            new DefaultTract<>(new TestResources())
        );

        assertThat(result.success()).isFalse();
        assertThat(result.errors().first().get().getClass()).isEqualTo(UnableToDecodeHttpRequestBody.class);
    }
}