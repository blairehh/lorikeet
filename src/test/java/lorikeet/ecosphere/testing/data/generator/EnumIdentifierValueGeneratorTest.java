package lorikeet.ecosphere.testing.data.generator;

import lorikeet.Err;
import lorikeet.ecosphere.Account;
import lorikeet.ecosphere.BankAccountType;
import lorikeet.ecosphere.testing.data.IdentifierValue;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.error.CouldNotFindEnumConstantFromEnumIdentifierValue;
import lorikeet.error.EnumIdentifierValueClassMismatch;
import lorikeet.error.IdentifierValueRequiredToGenerateEnum;
import lorikeet.error.ValueGeneratorDoesNotSupportJavaType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumIdentifierValueGeneratorTest {

    private final EnumIdentifierValueGenerator generator = new EnumIdentifierValueGenerator();

    @Test
    public void testJustEnumName() {
        BankAccountType type = generator.generate(BankAccountType.class, new IdentifierValue("SAVINGS")).orPanic();
        assertThat(type).isEqualTo(BankAccountType.SAVINGS);
    }

    @Test
    public void testWholeName() {
        BankAccountType type = generator.generate(BankAccountType.class, new IdentifierValue("lorikeet.ecosphere.BankAccountType.CREDIT")).orPanic();
        assertThat(type).isEqualTo(BankAccountType.CREDIT);
    }

    @Test
    public void testConstantNotFound() {
        Err<BankAccountType> result = generator.generate(BankAccountType.class, new IdentifierValue("FOO"));
        assertThat(result.failedWith(CouldNotFindEnumConstantFromEnumIdentifierValue.class)).isTrue();
    }

    @Test
    public void testConstantNotFoundWithFullName() {
        Err<BankAccountType> result = generator.generate(BankAccountType.class, new IdentifierValue("lorikeet.ecosphere.BankAccountType.FOO"));
        assertThat(result.failedWith(CouldNotFindEnumConstantFromEnumIdentifierValue.class)).isTrue();
    }

    @Test
    public void testClassNameMismatch() {
        Err<BankAccountType> result = generator.generate(BankAccountType.class, new IdentifierValue("foo.Bar.CREDIT"));
        assertThat(result.failedWith(EnumIdentifierValueClassMismatch.class)).isTrue();
    }

    @Test
    public void testValueMustIdentifierValue() {
        Err<BankAccountType> result = generator.generate(BankAccountType.class, new NumberValue(3));
        assertThat(result.failedWith(IdentifierValueRequiredToGenerateEnum.class)).isTrue();
    }

    @Test
    public void testDoesNotTryOnNonEnumClass() {
        Err<Account> result = generator.generate(Account.class, new IdentifierValue("SAVINGS"));
        assertThat(result.failedWith(ValueGeneratorDoesNotSupportJavaType.class)).isTrue();
    }
}