package lorikeet.parse;

import lorikeet.error.SingularExpressionNotValue;
import lorikeet.error.UnexpectedEof;
import lorikeet.error.UnsupportedQuotation;
import lorikeet.error.ExpectedExpression;
import lorikeet.error.ExpressionLacksValue;
import lorikeet.error.UnknownToken;
import lorikeet.lang.Let;
import lorikeet.lang.Expression;
import lorikeet.lang.Expressionable;
import lorikeet.lang.Value.StrLiteral;
import lorikeet.lang.Value.IntLiteral;
import lorikeet.lang.Value.DecLiteral;
import lorikeet.lang.Value.BolLiteral;
import lorikeet.lang.Value.Invocation;
import lorikeet.lang.Value.Variable;
import lorikeet.token.TokenSeq;
import lorikeet.token.TokenType;
import lorikeet.token.Keyword;
import lorikeet.token.Symbol;
import lorikeet.token.QuotationToken;

import java.util.List;
import java.util.ArrayList;

public class ExpressionParser implements Parser<Expression> {

    private final VariableTable variableTable;
    private final TypeParser typeParser;
    private final List<String> endTokens;

    public ExpressionParser(VariableTable vt, TypeParser typeParser) {
        this.variableTable = new VariableTable(vt);
        this.typeParser = typeParser;
        this.endTokens = new ArrayList<String>();
    }

    public ExpressionParser(VariableTable vt, TypeParser typeParser, List<String> endTokens) {
        this.variableTable = new VariableTable(vt);
        this.typeParser = typeParser;
        this.endTokens = endTokens;
    }

    @Override
    public Parse<Expression> parse(TokenSeq tokens) {
        if (tokens.current().isKeyword(Keyword.LET) && this.endTokens.isEmpty()) {
            return new Parse<Expression>(new SingularExpressionNotValue(tokens));
        }
        return this.parse(tokens, new ArrayList<Expressionable>());
    }

    public Parse<Expression> parse(TokenSeq tokens, List<Expressionable> list) {
        if (this.endTokens.isEmpty() && list.size() == 1) {
            return this.finish(tokens, list);
        }

        if (tokens.eof() && this.endTokens.size() != 0) {
            return new Parse<Expression>(new UnexpectedEof(tokens));
        }

        if (tokens.eof()) {
            return this.finish(tokens, list);
        }

        if (this.endTokens.contains(tokens.currentStr())) {
            return this.finish(tokens, list);
        }

        if (this.variableTable.get(tokens.currentStr()).isPresent()) {
            return this.parseVar(tokens, list);
        }

        if (tokens.current().getTokenType() == TokenType.QUOTATION) {
            return this.parseQuotation(tokens, list);
        }

        if (tokens.current().getTokenType() == TokenType.NUMBER) {
            return this.parseNumeric(tokens, list);
        }

        if (tokens.currentStr().equals("true") || tokens.currentStr().equals("false")) {
            return this.parseBoolean(tokens, list);
        }

        if (tokens.current().isKeyword(Keyword.LET)) {
            return this.parseLet(tokens, list);
        }

        if (tokens.current().isSymbol(Symbol.OPEN_ROUND)) {
            return this.parseInvoke(tokens.skip(), list);
        }

        return new Parse<Expression>(new UnknownToken(tokens));
    }

    private Parse<Expression> finish(TokenSeq tokens, List<Expressionable> list) {
        if (list.size() == 0) {
            return new Parse<Expression>(new ExpectedExpression(tokens));
        }
        if (!list.get(list.size() - 1).getExpressionType().isPresent()) {
            return new Parse<Expression>(new ExpressionLacksValue(tokens));
        } else {
            return new Parse<Expression>(
                new Expression(
                    list,
                    list.get(list.size() - 1).getExpressionType().get()
                ),
                tokens
            );
        }
    }

    private Parse<Expression> parseQuotation(TokenSeq tokens, List<Expressionable> list) {
        QuotationToken token = (QuotationToken)tokens.current();
        if (token.getQuote().equals("`")) {
            return new Parse<Expression>(new UnsupportedQuotation(tokens));
        }
        if (token.getQuote().equals("'")) {
            return new Parse<Expression>(new UnsupportedQuotation(tokens));
        }
        list.add(new StrLiteral(tokens.currentStr()));
        return this.parse(tokens.jump(), list);
    }

    private Parse<Expression> parseBoolean(TokenSeq tokens, List<Expressionable> list) {
        list.add(new BolLiteral(tokens.currentStr()));
        return this.parse(tokens.jump(), list);
    }

    private Parse<Expression> parseNumeric(TokenSeq tokens, List<Expressionable> list) {
        if (tokens.currentStr().contains(".")) {
            list.add(new DecLiteral(tokens.currentStr()));
        } else {
            list.add(new IntLiteral(tokens.currentStr()));
        }
        return this.parse(tokens.jump(), list);
    }

    private Parse<Expression> parseVar(TokenSeq tokens, List<Expressionable> list) {
        list.add(this.variableTable.get(tokens.currentStr()).get());
        return this.parse(tokens.jump(), list);
    }

    private Parse<Expression> parseLet(TokenSeq tokens, List<Expressionable> list) {
        return new LetParser(this.variableTable, this.typeParser).parse(tokens.skip())
            .then((let, tokenSeq) -> {
                list.add(let);
                this.variableTable.add(let);
                return this.parse(tokenSeq, list);
        });
    }

    private Parse<Expression> parseInvoke(TokenSeq tokens, List<Expressionable> list) {
        return new InvokeParser(this.variableTable, this.typeParser).parse(tokens)
            .then((invoke, tokenSeq) -> {
                list.add(new Invocation(invoke));
                return this.parse(tokenSeq, list);
            });
    }
}
