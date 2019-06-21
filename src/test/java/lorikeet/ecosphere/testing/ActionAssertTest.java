package lorikeet.ecosphere.testing;


import lorikeet.ecosphere.CreateSavingsDeposit;
import lorikeet.ecosphere.IssueDebitCard;
import org.junit.ComparisonFailure;
import org.junit.Test;

import static lorikeet.ecosphere.testing.ActionAssert.verify;
import static lorikeet.ecosphere.testing.TestCase.test;

public class ActionAssertTest {

    @Test
    public void testActionYields() {
        TestCase<Boolean> test = test(new IssueDebitCard(), "masterCard");

        verify(test)
            .yields(true)
            .isCorrect();
    }

    @Test(expected = ComparisonFailure.class)
    public void testYieldsWrongValue() {
        TestCase<Boolean> test = test(new IssueDebitCard(), "masterCard");

        verify(test)
            .yields(false)
            .isCorrect();
    }

    @Test
    public void testActionThrows() {
        TestCase<Boolean> test = test(new CreateSavingsDeposit(), 1.0);

        verify(test)
            .raises(RuntimeException.class)
            .isCorrect();
    }

    @Test(expected = ComparisonFailure.class)
    public void testActionThrowsWrongException() {
        TestCase<Boolean> test = test(new CreateSavingsDeposit(), 1.0);

        verify(test)
            .raises(NullPointerException.class)
            .isCorrect();
    }
}