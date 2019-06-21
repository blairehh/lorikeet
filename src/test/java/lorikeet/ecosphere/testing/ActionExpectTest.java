package lorikeet.ecosphere.testing;

import lorikeet.ecosphere.BankAccountType;
import lorikeet.ecosphere.ChardRegistrationFee;
import lorikeet.ecosphere.ChargePayment;
import lorikeet.ecosphere.CreateSavingsDeposit;
import lorikeet.ecosphere.IssueDebitCard;
import lorikeet.ecosphere.SendWelcomeMessage;
import org.junit.ComparisonFailure;
import org.junit.Test;

import static lorikeet.ecosphere.testing.ActionExpect.expect;
import static lorikeet.ecosphere.testing.TestCase.test;

public class ActionExpectTest {

    @Test
    public void testActionYields() {
        TestCase<Boolean> test = test(new IssueDebitCard(), "masterCard");

        expect(test)
            .toReturn(true)
            .isCorrect();
    }

    @Test(expected = ComparisonFailure.class)
    public void testYieldsWrongValue() {
        TestCase<Boolean> test = test(new IssueDebitCard(), "masterCard");

        expect(test)
            .toReturn(false)
            .isCorrect();
    }

    @Test
    public void testActionThrows() {
        TestCase<Boolean> test = test(new CreateSavingsDeposit(), 1.0);

        expect(test)
            .toThrow(RuntimeException.class)
            .isCorrect();
    }

    @Test(expected = ComparisonFailure.class)
    public void testActionThrowsWrongException() {
        TestCase<Boolean> test = test(new CreateSavingsDeposit(), 1.0);

        expect(test)
            .toThrow(NullPointerException.class)
            .isCorrect();
    }

    @Test
    public void testInteraction() {
        TestCase<Double> test = test(new ChardRegistrationFee(), BankAccountType.CHEQUE);
        expect(test)
            .toYield(new ChargePayment(), "USD", 2.0)
            .isCorrect();
    }

    @Test(expected = AssertionError.class)
    public void testInteractionNotSatisfiedDueToCellNotInvoked() {
        TestCase<Double> test = test(new ChardRegistrationFee(), BankAccountType.CHEQUE);
        expect(test)
            .toYield(new SendWelcomeMessage(), "", "")
            .isCorrect();
    }

    @Test(expected = AssertionError.class)
    public void testInteractionNotSatisfiedDueToDifferentArgument() {
        TestCase<Double> test = test(new ChardRegistrationFee(), BankAccountType.CHEQUE);
        expect(test)
            .toYield(new ChargePayment(), "USD", 3.0)
            .isCorrect();
    }
}