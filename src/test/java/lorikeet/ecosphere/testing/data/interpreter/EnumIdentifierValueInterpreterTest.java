package lorikeet.ecosphere.testing.data.interpreter;

import lorikeet.ecosphere.Account;
import lorikeet.ecosphere.BankAccountType;
import lorikeet.ecosphere.testing.data.IdentifierValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumIdentifierValueInterpreterTest {

    private final EnumIdentifierValueInterpreter interpreter = new EnumIdentifierValueInterpreter();

    @Test
    public void testEnum() {
        assertThat(interpreter.interpret(BankAccountType.SAVINGS).orPanic())
            .isEqualTo(new IdentifierValue("lorikeet.ecosphere.BankAccountType.SAVINGS"));
    }

    @Test
    public void testNonEnumTypeIsEmpty() {
        assertThat(interpreter.interpret(new Account()).isPresent()).isFalse();
    }
}