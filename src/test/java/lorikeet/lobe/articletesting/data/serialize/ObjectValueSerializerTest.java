package lorikeet.lobe.articletesting.data.serialize;

import lorikeet.Dict;
import lorikeet.lobe.articletesting.data.NullValue;
import lorikeet.lobe.articletesting.data.NumberValue;
import lorikeet.lobe.articletesting.data.ObjectValue;
import lorikeet.lobe.articletesting.data.StringValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectValueSerializerTest {

    private final ObjectValueSerializer serializer = new ObjectValueSerializer();

    @Test
    public void test() {

        ObjectValue account = new ObjectValue(
            "lorikeet.lobe.Account",
            Dict.of("id", new StringValue("FG5464"), "balance", new NumberValue(45.33))
        );

        ObjectValue user = new ObjectValue(
            "lorikeet.lobe.User",
            Dict.of("email", new StringValue("foo@gmail.com"), "welcomeMessageSentAt", new NullValue())
                .push("password", new StringValue("secret"))
                .push("account", account)
        );

        assertThat(serializer.serialize(user).orPanic())
            .isEqualTo("lorikeet.lobe.User(account=lorikeet.lobe.Account(balance=45.33, id='FG5464'), email='foo@gmail.com', password='secret', welcomeMessageSentAt=null)");
    }

}