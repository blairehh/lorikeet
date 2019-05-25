package lorikeet.ecosphere.testing.data;

import lorikeet.Dict;
import lorikeet.Opt;

import java.util.Objects;

public class CellValue implements Value {
    private final String className;
    private final Value exceptionThrown;
    private final Value returnValue;
    private final Dict<String, Value> arguments;

    public CellValue(String className, Dict<String, Value> arguments, Value exceptionThrown, Value returnValue) {
        this.className = className;
        this.arguments = arguments;
        this.exceptionThrown = exceptionThrown;
        this.returnValue = returnValue;
    }

    public String getClassName() {
        return this.className;
    }

    public Dict<String, Value> getArguments() {
        return this.arguments;
    }

    public Opt<Value> getExceptionThrown() {
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

        CellValue that = (CellValue) o;

        return Objects.equals(this.getClassName(), that.getClassName())
            && Objects.equals(this.getArguments(), that.getArguments())
            && Objects.equals(this.getExceptionThrown(), that.getExceptionThrown())
            && Objects.equals(this.getReturnValue(), that.getReturnValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClassName(), this.getArguments(), this.getExceptionThrown(), this.getReturnValue());
    }
}
