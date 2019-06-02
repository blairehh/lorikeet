package lorikeet.ecosphere.testing.article;

import lorikeet.ecosphere.testing.data.Value;

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
