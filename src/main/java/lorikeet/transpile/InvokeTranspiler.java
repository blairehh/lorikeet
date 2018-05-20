package lorikeet.transpile;

import lorikeet.lang.Invoke;
import lorikeet.lang.Value;

public class InvokeTranspiler {

    public String transpile(Invoke invoke) {
        return String.format(
            "%s.%s(%s)",
            this.transpileValue(invoke.getSubject()),
            this.transpileFunctionName(invoke),
            this.transpileArguments(invoke)
        );
    }

    private String transpileValue(Value value) {
        if (value instanceof Value.Variable) {
            return this.transpileVar(value);
        }
        if (value instanceof Value.StrLiteral) {
            return this.transpileStr(value);
        }
        if (value instanceof Value.BolLiteral) {
            return this.transpileBol(value);
        }
        if (value instanceof Value.IntLiteral) {
            return this.transpileInt(value);
        }
        if (value instanceof Value.DecLiteral) {
            return this.transpileDec(value);
        }
        return "";
    }

    private String transpileVar(Value value) {
        final Value.Variable var = (Value.Variable)value;
        return String.format("v_%s", var.getName());
    }

    private String transpileStr(Value value) {
        final Value.StrLiteral literal = (Value.StrLiteral)value;
        return String.format("new lorikeet.core.Lk_struct_Str(\"%s\")", literal.getValue());
    }

    private String transpileBol(Value value) {
        final Value.BolLiteral literal = (Value.BolLiteral)value;
        return String.format("new lorikeet.core.Lk_struct_Bol(%s)", literal.getValue());
    }

    private String transpileInt(Value value) {
        final Value.IntLiteral literal = (Value.IntLiteral)value;
        return String.format("new lorikeet.core.Lk_struct_Int(%sL)", literal.getValue());
    }

    private String transpileDec(Value value) {
        final Value.DecLiteral literal = (Value.DecLiteral)value;
        return String.format("new lorikeet.core.Lk_struct_Dec(%s)", literal.getValue());
    }

    private String transpileFunctionName(Invoke invoke) {
        return String.format("m_%s", invoke.getFunctionName());
    }

    private String transpileArguments(Invoke invoke) {
        StringBuilder builder = new StringBuilder();
        for (Value arg : invoke.getArguments()) {
            builder.append(this.transpileValue(arg));
            builder.append(", ");
        }
        if (builder.length() != 0) {
            builder.replace(builder.length() - 2, builder.length(), "");
        }
        return builder.toString();
    }
}
