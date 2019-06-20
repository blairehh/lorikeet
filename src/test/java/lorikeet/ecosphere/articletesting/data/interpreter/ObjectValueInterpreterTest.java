package lorikeet.ecosphere.articletesting.data.interpreter;


import lorikeet.ecosphere.Account;
import lorikeet.ecosphere.User;
import lorikeet.ecosphere.articletesting.data.NullValue;
import lorikeet.ecosphere.articletesting.data.NumberValue;
import lorikeet.ecosphere.articletesting.data.ObjectValue;
import lorikeet.ecosphere.articletesting.data.StringValue;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class ObjectValueInterpreterTest {

    private final ObjectValueInterpreter interpreter = new ObjectValueInterpreter();

    @Test
    public void testDoesNotInterpretJavaLangInstance() {
        Thread thread = new Thread();
        assertThat(interpreter.interpret(thread).isPresent()).isFalse();
    }

    @Test
    public void testObject() {
        User user = new User();
        user.email = "foo@gmail.com";
        user.password = "secret";

        Account account = new Account();
        account.balance = 45.33;
        user.account = account;

        ObjectValue object = (ObjectValue)this.interpreter.interpret(user).orPanic();

        assertThat(object.getClassName()).isEqualTo("lorikeet.ecosphere.User");
        assertThat(object.getData()).containsEntry("email", new StringValue("foo@gmail.com"));
        assertThat(object.getData()).containsEntry("password", new StringValue("secret"));
        assertThat(object.getData()).containsEntry("welcomeMessageSentAt", new NullValue());
        final ObjectValue accountValue = ((ObjectValue)object.getData().find("account").orPanic());
        assertThat(accountValue.getData()).containsEntry("balance", new NumberValue(45.33));
        assertThat(accountValue.getData()).containsEntry("id", new StringValue("FG5464"));
    }

}