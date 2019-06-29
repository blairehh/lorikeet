package lorikeet.lobe.articletesting.data.interpreter;

import lorikeet.lobe.Account;
import lorikeet.lobe.BankAccountType;
import lorikeet.lobe.articletesting.data.IdentifierValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumIdentifierValueInterpreterTest {

    private final EnumIdentifierValueInterpreter interpreter = new EnumIdentifierValueInterpreter();

    @Test
    public void testEnum() {
        assertThat(interpreter.interpret(BankAccountType.SAVINGS).orPanic())
            .isEqualTo(new IdentifierValue("lorikeet.lobe.BankAccountType.SAVINGS"));
    }

    @Test
    public void testNonEnumTypeIsEmpty() {
        assertThat(interpreter.interpret(new Account()).isPresent()).isFalse();
    }
}