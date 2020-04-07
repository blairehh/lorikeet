package lorikeet.http;

import lorikeet.core.DictOf;
import lorikeet.http.internal.HttpMsgPath;
import org.junit.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class StringPathVarTest {

    private HttpMsgPath path = new HttpMsgPath(
        URI.create("/orders/1234"),
        new DictOf<String, String>().push("id", "1234")
    );


    @Test
    public void testNullName() {
        boolean succeeded = new StringPathVar(path, null)
            .include()
            .success();

        assertThat(succeeded).isFalse();
    }

    @Test
    public void testPathVarNotFound() {
        boolean succeeded = new StringPathVar(path, "not-there")
            .include()
            .success();

        assertThat(succeeded).isFalse();
    }
    @Test
    public void testFindsPathVar() {
        String value = new StringPathVar(path, "id")
            .include()
            .orPanic();

        assertThat(value).isEqualTo("1234");
    }

}