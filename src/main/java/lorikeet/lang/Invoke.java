package lorikeet.lang;

import java.util.List;
import java.util.Objects;

public class Invoke {
    private final Expression subject;
    private final String funcName;
    private final List<Value> arguments;

    public Invoke(Expression subject, String funcName, List<Value> args) {
        this.subject = subject;
        this.funcName = funcName;
        this.arguments = args;
    }

    public Expression getSubject() {
        return this.subject;
    }

    public String getFunctionName() {
        return this.funcName;
    }

    public List<Value> getArguments() {
        return this.arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Invoke that = (Invoke)o;

        return Objects.equals(this.getSubject(), that.getSubject())
            && Objects.equals(this.getFunctionName(), that.getFunctionName())
            && Objects.equals(this.getArguments(), that.getArguments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.subject, this.funcName, this.arguments);
    }
}
