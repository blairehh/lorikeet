package lorikeet.transpile;

import lorikeet.lang.Let;
import lorikeet.lang.SpecType.Known;

public class LetTranspiler {

    public String transpile(Let let) {
        return String.format(
            "final %s v_%s =",
            this.transpileType(let),
            let.getName()
        );
    }

    private String transpileType(Let let) {
        if (!(let.getType() instanceof Known)) {
            return ""; // this should never happen and is a bug in the compiler if so
        }
        return ((Known)let.getType()).getType().toString();
    }


}
