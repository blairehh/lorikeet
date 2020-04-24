package lorikeet.http;

import lorikeet.core.DictOf;
import lorikeet.http.internal.HttpMsgPath;
import org.junit.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class IntPathVarTest {

    private HttpMsgPath path = new HttpMsgPath(
        URI.create("/orders/1234"),
        new DictOf<String, String>().push("id", "1234").push("a-string", "abcd")
    );

    @Test
    public void testValidVariable() {
        final Integer value = new IntPathVar(path, "id")
            .include(null)
            .orPanic();

        assertThat(value).isEqualTo(1234);
    }

    @Test
    public void testNotFound() {
        final boolean success = new IntPathVar(path, "not-there")
            .include(null)
            .success();

        assertThat(success).isFalse();
    }

    @Test
    public void testNotAValidNumber() {
        final boolean success = new IntPathVar(path, "a-string")
            .include(null)
            .success();

        assertThat(success).isFalse();
    }

    @Test
    public void testNotAValidPathVarName() {
        final boolean success = new IntPathVar(path, "")
            .include(null)
            .success();

        assertThat(success).isFalse();
    }
}