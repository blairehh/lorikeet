package lorikeet.ecosphere.transcript.serialize;

import lorikeet.Dict;
import lorikeet.ecosphere.transcript.NullValue;
import lorikeet.ecosphere.transcript.NumberValue;
import lorikeet.ecosphere.transcript.ObjectValue;
import lorikeet.ecosphere.transcript.StringValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectValueSerializerTest {

    private final ObjectValueSerializer serializer = new ObjectValueSerializer();

    @Test
    public void test() {

        ObjectValue account = new ObjectValue(
            "lorikeet.ecosphere.Account",
            Dict.of("id", new StringValue("FG5464"), "balance", new NumberValue(45.33))
        );

        ObjectValue user = new ObjectValue(
            "lorikeet.ecosphere.User",
            Dict.of("email", new StringValue("foo@gmail.com"), "welcomeMessageSentAt", new NullValue())
                .push("password", new StringValue("secret"))
                .push("account", account)
        );

        assertThat(serializer.serialize(user).orPanic())
            .isEqualTo("lorikeet.ecosphere.User(account=lorikeet.ecosphere.Account(balance=45.33, id='FG5464'), email='foo@gmail.com', password='secret', welcomeMessageSentAt=null)");
    }

}