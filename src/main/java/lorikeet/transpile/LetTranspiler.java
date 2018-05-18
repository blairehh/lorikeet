package lorikeet.transpile;

import lorikeet.lang.Let;
import lorikeet.lang.Value;
import lorikeet.lang.Value.StrLiteral;
import lorikeet.lang.Value.IntLiteral;
import lorikeet.lang.Value.BolLiteral;
import lorikeet.lang.Value.DecLiteral;

public class LetTranspiler {

    public String transpile(Let let) {
        return String.format(
            "final %s v_%s = %s;",
            let.getType().toString(),
            let.getName(),
            rightHandValue(let)
        );
    }

    private String rightHandValue(Let let) {
        if (let.getValue() instanceof StrLiteral) {
            return String.format(
                "new %s(%s)",
                let.getType().toString(),
                let.getValue().toString()
            );
        }
        if (let.getValue() instanceof IntLiteral) {
            return String.format(
                "new %s(%sL)",
                let.getType().toString(),
                let.getValue().toString()
            );
        }
        if (let.getValue() instanceof BolLiteral) {
            return String.format(
                "new %s(%s)",
                let.getType().toString(),
                let.getValue().toString()
            );
        }
        if (let.getValue() instanceof DecLiteral) {
            return String.format(
                "new %s(%s)",
                let.getType().toString(),
                let.getValue().toString()
            );
        }
        return "";
    }
}
