package lorikeet.ecosphere.testing;

import org.junit.ComparisonFailure;

import java.util.Objects;

public class ActionExpect<T> {

    private final TestCase<T> testCase;
    private boolean checkReturn;
    private T expectedReturn;
    private boolean checkException;
    private Class<? extends Exception> expectedException;

    public ActionExpect(TestCase<T> testCase) {
        this.testCase = testCase;
        this.checkReturn = false;
        this.expectedReturn = null;
        this.checkException = false;
        this.expectedException = null;
    }

    public static <T> ActionExpect<T> expect(TestCase<T> testCase) {
        return new ActionExpect<>(testCase);
    }

    public ActionExpect toReturn(T value) {
        this.checkReturn = true;
        this.expectedReturn = value;
        return this;
    }

    public ActionExpect toThrow(Class<? extends Exception> e) {
        this.checkException = true;
        this.expectedException = e;
        return this;
    }

    public void isCorrect() {
        T actualReturn = null;
        Exception actualException = null;

        try {
            actualReturn = this.testCase.execute();
        } catch (Exception e) {
            actualException = e;
        }

        if (this.checkReturn) {
            assertEquals("return value did not match", expectedReturn, actualReturn);
        }

        if (this.checkException) {
            final Class<? extends Exception> actualExceptionClass = actualException == null
                ? null
                : actualException.getClass();
            assertEquals("exception did not match", expectedException, actualExceptionClass);
        }

    }

    private void assertEquals(String message, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) {
            return;
        }
        final String expectedString = expected == null
            ? null
            : expected.toString();
        final String actualString = actual == null
            ? null
            : actual.toString();

        throw new ComparisonFailure(message, expectedString, actualString);
    }

}
