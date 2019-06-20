package lorikeet.ecosphere.articletesting.article;

import lorikeet.ecosphere.articletesting.data.CellDefinition;
import lorikeet.ecosphere.articletesting.data.EqualityChecker;
import lorikeet.ecosphere.articletesting.data.Value;

import java.util.Objects;

public class RunResult {
    private final Value returnValue;
    private final boolean returnValueMatched;
    private final String exceptionThrown;
    private final boolean exceptionThrownMatched;

    public RunResult(
        Value returnValue,
        boolean returnValueMatched,
        String exceptionThrown,
        boolean exceptionThrownMatched
    ) {
        this.returnValue = returnValue;
        this.returnValueMatched = returnValueMatched;
        this.exceptionThrown = exceptionThrown;
        this.exceptionThrownMatched = exceptionThrownMatched;
    }

    public static RunResult resultForReturn(CellDefinition specified, Value actualReturnValue) {
        final EqualityChecker equality = new EqualityChecker();

        final boolean returnMatched = specified.getReturnValue()
            .map(value ->  equality.checkEquality(value, actualReturnValue))
            .orElse(false);

        final boolean exceptionMatched = !specified.getExceptionThrown().isPresent();

        return new RunResult(actualReturnValue, returnMatched, null, exceptionMatched);
    }

    public static RunResult resultForException(CellDefinition specified, String exception) {

        final boolean exceptionMatched = specified.getExceptionThrown()
            .map(exc -> exc.equals(exception))
            .orElse(false);

        final boolean returnMatched = !specified.getReturnValue().isPresent();

        return new RunResult(null, returnMatched, exception, exceptionMatched);
    }

    public Value getReturnValue() {
        return this.returnValue;
    }

    public boolean isReturnValueMatched() {
        return this.returnValueMatched;
    }

    public String getExceptionThrown() {
        return this.exceptionThrown;
    }

    public boolean isExceptionThrownMatched() {
        return this.exceptionThrownMatched;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        RunResult runResult = (RunResult) o;

        return Objects.equals(this.isReturnValueMatched(), runResult.isReturnValueMatched())
            && Objects.equals(this.isExceptionThrownMatched(), runResult.isExceptionThrownMatched())
            && Objects.equals(this.getReturnValue(), runResult.getReturnValue())
            && Objects.equals(this.getExceptionThrown(), runResult.getExceptionThrown());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.getReturnValue(),
            this.isReturnValueMatched(),
            this.getExceptionThrown(),
            this.isExceptionThrownMatched()
        );
    }
}
