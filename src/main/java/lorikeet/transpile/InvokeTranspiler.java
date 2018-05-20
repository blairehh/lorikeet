package lorikeet.transpile;

import lorikeet.lang.Invoke;
import lorikeet.lang.Value;

public class InvokeTranspiler {

    private final ValueTranspiler value;

    public InvokeTranspiler() {
        this.value = new ValueTranspiler(this);
    }

    public InvokeTranspiler(ValueTranspiler value) {
        this.value = value;
    }

    public String transpile(Invoke invoke) {
        return String.format(
            "%s.%s(%s)",
            this.value.transpile(invoke.getSubject()),
            this.transpileFunctionName(invoke),
            this.transpileArguments(invoke)
        );
    }

    private String transpileFunctionName(Invoke invoke) {
        return String.format("m_%s", invoke.getFunctionName());
    }

    private String transpileArguments(Invoke invoke) {
        StringBuilder builder = new StringBuilder();
        for (Value arg : invoke.getArguments()) {
            builder.append(this.value.transpile(arg));
            builder.append(", ");
        }
        if (builder.length() != 0) {
            builder.replace(builder.length() - 2, builder.length(), "");
        }
        return builder.toString();
    }
}
