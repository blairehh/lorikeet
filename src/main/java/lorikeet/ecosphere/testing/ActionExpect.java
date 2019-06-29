package lorikeet.ecosphere.testing;

import lorikeet.Seq;
import lorikeet.ecosphere.Action1;
import lorikeet.ecosphere.Action2;
import lorikeet.ecosphere.Action3;
import lorikeet.ecosphere.Action4;
import lorikeet.ecosphere.Action5;
import org.junit.ComparisonFailure;

import java.util.Objects;
import java.util.stream.Collectors;

public class ActionExpect<T> {

    private final TestCase<T> testCase;
    private boolean checkReturn;
    private T expectedReturn;
    private boolean checkException;
    private Class<? extends Exception> expectedException;
    protected Interaction interaction;
    protected Seq<Interaction> expectInteractions;

    public ActionExpect(TestCase<T> testCase) {
        this.testCase = testCase;
        this.checkReturn = false;
        this.expectedReturn = null;
        this.checkException = false;
        this.expectedException = null;
        this.interaction = null;
        this.expectInteractions = Seq.empty();
    }

    public ActionExpect(ActionExpect<T> expect) {
        this.testCase = expect.testCase;
        this.checkReturn = expect.checkReturn;
        this.expectedReturn = expect.expectedReturn;
        this.checkException = expect.checkException;
        this.expectedException = expect.expectedException;
        this.interaction = expect.interaction;
        this.expectInteractions = expect.expectInteractions;
    }

    public static <T> ActionExpect<T> expect(TestCase<T> testCase) {
        return new ActionExpect<>(testCase);
    }

    public ActionExpect<T> toReturn(T value) {
        this.syncInteractions();
        this.checkReturn = true;
        this.expectedReturn = value;
        return this;
    }

    public ActionExpect<T> toThrow(Class<? extends Exception> e) {
        this.syncInteractions();
        this.checkException = true;
        this.expectedException = e;
        return this;
    }

    public <YieldReturn, YieldParameter1> YieldExpect<T, YieldReturn> toYield(
        Action1<YieldReturn, YieldParameter1> yield,
        YieldParameter1 parameter1
    ) {
        this.syncInteractions();
        this.interaction = new Interaction(yield, Seq.of(parameter1));
        return new YieldExpect<>(this);
    }

    public <YieldReturn, YieldParameter1, YieldParameter2> YieldExpect<T, YieldReturn> toYield(
        Action2<YieldReturn, YieldParameter1, YieldParameter2> yield,
        YieldParameter1 parameter1,
        YieldParameter2 parameter2
    ) {
        this.syncInteractions();
        this.interaction = new Interaction(yield, Seq.of(parameter1, parameter2));
        return new YieldExpect<>(this);
    }

    public <YR, YP1, YP2, YP3> YieldExpect<T, YR> toYield(
        Action3<YR, YP1, YP2, YP3> yield,
        YP1 parameter1,
        YP2 parameter2,
        YP3 parameter3
    ) {
        this.syncInteractions();
        this.interaction = new Interaction(yield, Seq.of(parameter1, parameter2, parameter3));
        return new YieldExpect<>(this);
    }

    public <YR, YP1, YP2, YP3, YP4> YieldExpect<T, YR> toYield(
        Action4<YR, YP1, YP2, YP3, YP4> yield,
        YP1 parameter1,
        YP2 parameter2,
        YP3 parameter3,
        YP4 parameter4
    ) {
        this.syncInteractions();
        this.interaction = new Interaction(yield, Seq.of(parameter1, parameter2, parameter3, parameter4));
        return new YieldExpect<>(this);
    }

    public <YR, YP1, YP2, YP3, YP4, YP5> YieldExpect<T, YR> toYield(
        Action5<YR, YP1, YP2, YP3, YP4, YP5> yield,
        YP1 parameter1,
        YP2 parameter2,
        YP3 parameter3,
        YP4 parameter4,
        YP5 parameter5
    ) {
        this.syncInteractions();
        this.interaction = new Interaction(yield, Seq.of(parameter1, parameter2, parameter3, parameter4, parameter5));
        return new YieldExpect<>(this);
    }


    public void isCorrect() {
        this.syncInteractions();
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

    private void syncInteractions() {
        if (this.interaction != null) {
            this.expectInteractions = this.expectInteractions.push(interaction);
            this.interaction = null;
        }
    }

    private void assertInteractions(Seq<Interaction> actualInteractions) {
        final Seq<Interaction> failedInteractions = this.expectInteractions.stream()
            .filter(interaction -> didInteractionFail(interaction, actualInteractions))
            .collect(Seq.collector());

        if (failedInteractions.isEmpty()) {
            return;
        }
        final String interactionsString = failedInteractions.stream()
            .map(Object::toString)
            .collect(Collectors.joining(","));

        final StringBuilder message = new StringBuilder();
        message.append("the following interactions were not satisfied\n");
        message.append(interactionsString);

        throw new AssertionError(message);
    }

    private boolean didInteractionFail(Interaction expected, Seq<Interaction> actualInteractions) {
        final Seq<Interaction> matchedInteractions = actualInteractions.stream()
            .filter(actual -> actual.invokeEquals(expected))
            .collect(Seq.collector());

        if (matchedInteractions.isEmpty()) {
            return true;
        }

        return matchedInteractions.stream()
            .anyMatch(actual -> !actual.outcomeIsApplicableTo(expected));

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
