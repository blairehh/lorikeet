package lorikeet.transpile;

import lorikeet.lang.Value;

public class ValueTranspiler {

    private final InvokeTranspiler invoke;

    public ValueTranspiler() {
        this.invoke = new InvokeTranspiler(this);
    }

    public ValueTranspiler(InvokeTranspiler invoke) {
        this.invoke = invoke;
    }

    public String transpile(Value value) {
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
        if (value instanceof Value.Invocation) {
            return this.transpileInvoke(value);
        }
        return ""; //@TODO throw/return compiler error
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

    private String transpileInvoke(Value value) {
        final Value.Invocation invocation = (Value.Invocation)value;
        return this.invoke.transpile(invocation.getInvoke());
    }


}
