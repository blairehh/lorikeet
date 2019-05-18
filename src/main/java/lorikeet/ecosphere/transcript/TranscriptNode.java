package lorikeet.ecosphere.transcript;

import lorikeet.Opt;

import java.util.Objects;

public class TranscriptNode {
    private final String className;
    private final String exceptionThrown;
    private final Value returnValue;

    public TranscriptNode(String className, String exceptionThrown, Value returnValue) {
        this.className = className;
        this.exceptionThrown = exceptionThrown;
        this.returnValue = returnValue;
    }

    public String getClassName() {
        return this.className;
    }

    public Opt<String> getExceptionThrown() {
        return Opt.ofNullable(this.exceptionThrown);
    }

    public Opt<Value> getReturnValue() {
        return Opt.ofNullable(this.returnValue);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        TranscriptNode that = (TranscriptNode) o;

        return Objects.equals(this.getClassName(), that.getClassName())
            && Objects.equals(this.getExceptionThrown(), that.getExceptionThrown())
            && Objects.equals(this.getReturnValue(), that.getReturnValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClassName(), this.getExceptionThrown(), this.getReturnValue());
    }
}
