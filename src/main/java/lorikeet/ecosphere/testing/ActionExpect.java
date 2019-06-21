package lorikeet.ecosphere.testing;

import lorikeet.Seq;
import lorikeet.ecosphere.Action1;
import lorikeet.ecosphere.Action2;
import org.junit.ComparisonFailure;

import java.util.Objects;
import java.util.stream.Collectors;

public class ActionExpect<T> {

    private final TestCase<T> testCase;
    private boolean checkReturn;
    private T expectedReturn;
    private boolean checkException;
    private Class<? extends Exception> expectedException;
    private Seq<Interaction> expectInteractions;

    public ActionExpect(TestCase<T> testCase) {
        this.testCase = testCase;
        this.checkReturn = false;
        this.expectedReturn = null;
        this.checkException = false;
        this.expectedException = null;
        this.expectInteractions = Seq.empty();
    }

    public static <T> ActionExpect<T> expect(TestCase<T> testCase) {
        return new ActionExpect<>(testCase);
    }

    public ActionExpect<T> toReturn(T value) {
        this.checkReturn = true;
        this.expectedReturn = value;
        return this;
    }

    public ActionExpect<T> toThrow(Class<? extends Exception> e) {
        this.checkException = true;
        this.expectedException = e;
        return this;
    }

    public <YieldReturn, YieldParameter1> ActionExpect<T> toYield(Action1<YieldReturn, YieldParameter1> yield, YieldParameter1 parameter1) {
        this.expectInteractions = this.expectInteractions.push(new Interaction(yield, Seq.of(parameter1)));
        return this;
    }

    public <YieldReturn, YieldParameter1, YieldParameter2> ActionExpect<T> toYield(
        Action2<YieldReturn, YieldParameter1, YieldParameter2> yield,
        YieldParameter1 parameter1,
        YieldParameter2 parameter2
    ) {
        this.expectInteractions = this.expectInteractions.push(new Interaction(yield, Seq.of(parameter1, parameter2)));
        return this;
    }

    public void isCorrect() {
        T actualReturn = null;
        Exception actualException = null;

        try {
            actualReturn = this.testCase.evaluate();
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

        if (!this.expectInteractions.isEmpty()) {
            this.assertInteractions(this.testCase.getInteractions());
        }

    }

    private void assertInteractions(Seq<Interaction> actualInteractions) {
      //  System.out.println(actualInteractions);
        final Seq<Interaction> failedInteractions = this.expectInteractions.stream()
            .filter(interaction -> !actualInteractions.contains(interaction))
            .collect(Seq.collector());

        if (failedInteractions.isEmpty()) {
            return;
        }
       // System.out.println(failedInteractions);
        final String interactoinsString = failedInteractions.stream()
            .map(Object::toString)
            .collect(Collectors.joining(","));

        final StringBuilder message = new StringBuilder();
        message.append("the following interactions were not satisfied\n");
        message.append(interactoinsString);

        throw new AssertionError(message);
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
