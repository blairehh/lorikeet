package lorikeet.parse;

import lorikeet.error.UnexpectedEof;
import lorikeet.lang.Expression;
import lorikeet.lang.Invoke;
import lorikeet.lang.Value;
import lorikeet.token.TokenSeq;
import lorikeet.token.Symbol;

import java.util.List;
import java.util.ArrayList;

public class InvokeParser implements Parser<Invoke> {

    private final ExpressionParser expressionParser;

    public InvokeParser(VariableTable vt, TypeParser typeParser) {
        this.expressionParser = new ExpressionParser(vt, typeParser);
    }

    public Parse<Invoke> parse(TokenSeq tokens) {
        if (tokens.eof()) {
            return new Parse<Invoke>(new UnexpectedEof(tokens));
        }
        return this.expressionParser.parse(tokens).then((expr, tokenSeq) -> {
            Parsing parsing = new Parsing();
            parsing.subject = (Value)expr.getChildren().get(0);
            if (tokenSeq.current().isSymbol(Symbol.CLOSE_ROUND)) {
                return this.parseRefInvoke(tokenSeq, parsing);
            }
            return this.parseFunctionName(tokenSeq, parsing);
        });
    }

    private Parse<Invoke> parseRefInvoke(TokenSeq tokens, Parsing parsing) {
        parsing.functionName = "_invoke";
        return new Parse<Invoke>(parsing.build(), tokens.skip());
    }

    private Parse<Invoke> parseFunctionName(TokenSeq tokens, Parsing parsing) {
        if (tokens.eof()) {
            return new Parse<Invoke>(new UnexpectedEof(tokens));
        }
        parsing.functionName = tokens.currentStr();
        return this.parseArgs(tokens.skip(), parsing);
    }

    private Parse<Invoke> parseArgs(TokenSeq tokens, Parsing parsing) {
        if (tokens.eof()) {
            return new Parse<Invoke>(new UnexpectedEof(tokens));
        }
        if (tokens.current().isSymbol(Symbol.CLOSE_ROUND)) {
            return new Parse<Invoke>(parsing.build(), tokens.skip());
        }
        return this.expressionParser.parse(tokens).then((expr, tokenSeq) -> {
            parsing.args.add((Value)expr.getChildren().get(0));
            return this.parseArgs(tokenSeq, parsing);
        });
    }

    private static class Parsing {
        public Value subject = null;
        public String functionName = null;
        public List<Value> args = new ArrayList<Value>();

        public Invoke build() {
            return new Invoke(subject, functionName, args);
        }
    }

}
