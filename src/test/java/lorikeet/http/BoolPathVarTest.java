package lorikeet.http;

import lorikeet.core.DictOf;
import lorikeet.http.internal.HttpMsgPath;
import org.junit.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class BoolPathVarTest {


    private HttpMsgPath path = new HttpMsgPath(
        URI.create("/orders/1234"),
        new DictOf<String, String>()
            .push("enabled", "true")
            .push("disabled", "false")
            .push("name", "Bob")
    );


    @Test
    public void testNullName() {
        boolean succeeded = new BoolPathVar(path, null)
            .include(null)
            .success();

        assertThat(succeeded).isFalse();
    }

    @Test
    public void testPathVarNotFound() {
        boolean succeeded = new BoolPathVar(path, "not-there")
            .include(null)
            .success();

        assertThat(succeeded).isFalse();
    }

    @Test
    public void testFindsTruePathVar() {
        boolean value = new BoolPathVar(path, "enabled")
            .include(null)
            .orPanic();

        assertThat(value).isTrue();
    }

    @Test
    public void testFindsFalsePathVar() {
        boolean value = new BoolPathVar(path, "disabled")
            .include(null)
            .orPanic();

        assertThat(value).isFalse();
    }

    @Test
    public void testPathVarWithBadValue() {
        boolean succeeded = new BoolPathVar(path, "name")
            .include(null)
            .success();

        assertThat(succeeded).isFalse();
    }
}