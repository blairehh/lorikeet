package lorikeet.compile;

import lorikeet.lang.LorikeetSource;
import lorikeet.lang.SourceFile;
import lorikeet.lang.Struct;
import lorikeet.lang.Function;
import lorikeet.lang.Expressionable;
import lorikeet.lang.SpecType;
import lorikeet.lang.Value;
import lorikeet.lang.Invoke;
import lorikeet.lang.LorikeetType;
import lorikeet.lang.Type;

public class TypeAssignChecker {

    public void check(LorikeetSource source, TypeRegistry registry) {
        for (SourceFile file : source.getResults()) {
            this.check(file);
        }
    }

    private void check(SourceFile sourceFile) {
        for (Struct struct : sourceFile.getStructs()) {
            for (Function func : struct.getFunctions()) {
                this.knowify(func, struct);
            }
        }
    }

    private void knowify(Function function, LorikeetType parent) {
        for (Expressionable e : function.getExpression().getChildren()) {
            // getExpressionType().isPresent)() is a little misleading
            // something better would be producedExpression()
            // i.e. does this thing actually produce a value
System.out.println(e.toString());
            // if (!e.getExpressionType().isPresent()) {
            //     continue;
            // }
            SpecType type = e.getExpressionType();

            if (type instanceof SpecType.ToBeKnown) {
                Type actuall = determineTypeFor((SpecType.ToBeKnown)type, parent);
                // System.out.println(e.toString());
                e.setExpressionType(new SpecType.Known(actuall));
                // System.out.println(e.toString());
            }
        }
    }

    private Type determineTypeFor(SpecType.ToBeKnown toBeKnown, LorikeetType parent) {
        final Value clue = toBeKnown.getClue();
        if (clue instanceof Value.Invocation) {
            return determineForInvoke(((Value.Invocation)clue).getInvoke(), parent);
        }
        throw new RuntimeException("could not get type");
    }

    private Type determineForInvoke(Invoke invoke, LorikeetType parent) {
        if (invoke.getSubject() instanceof Value.Self) {
            for (Function func : parent.getFunctions()) {
                // We also need to match how many args not just function name
                if (func.getName().equals(invoke.getFunctionName())) {
                    return func.getReturnType();
                }
            }
        }
        throw new RuntimeException("could not get type");
    }
}
