package lorikeet.parse;

import lorikeet.error.InvalidVariableName;
import lorikeet.error.BadSyntax;
import lorikeet.error.UnexpectedEof;
import lorikeet.error.UnsupportedQuotation;
import lorikeet.error.LiteralMismatch;
import lorikeet.error.TypeMismatch;
import lorikeet.error.NotImplementedYet;
import lorikeet.error.VariableNameConflict;
import lorikeet.lang.Expression;
import lorikeet.lang.Let;
import lorikeet.lang.Package;
import lorikeet.lang.Type;
import lorikeet.lang.Value.StrLiteral;
import lorikeet.lang.Value.IntLiteral;
import lorikeet.lang.Value.DecLiteral;
import lorikeet.lang.Value.BolLiteral;
import lorikeet.sdk.types.Str;
import lorikeet.sdk.types.Bol;
import lorikeet.sdk.types.Int;
import lorikeet.sdk.types.Dec;
import lorikeet.token.TokenSeq;
import lorikeet.token.Symbol;
import lorikeet.token.TokenType;
import lorikeet.token.QuotationToken;

public class LetParser implements Parser<Let> {

    private final TypeParser typeParser;
    private final VariableTable varTable;
    private final ExpressionParser expressionParser;

    public LetParser(VariableTable varTable, TypeTable typeTable, Package pkg) {
        this.typeParser = new TypeParser(typeTable, pkg);
        this.varTable = varTable;
        this.expressionParser = new ExpressionParser(varTable, this.typeParser);
    }

    public LetParser(VariableTable varTable, TypeParser typeParser) {
        this.typeParser = typeParser;
        this.varTable = varTable;
        this.expressionParser = new ExpressionParser(varTable, this.typeParser);
    }

    @Override
    public Parse<Let> parse(TokenSeq tokens) {
        final String name = tokens.currentStr();
        if (!Patterns.isVariableName(name)) {
            return new Parse<Let>(new InvalidVariableName(tokens));
        }
        if (varTable.get(name).isPresent()) {
            return new Parse<Let>(new VariableNameConflict(tokens, name));
        }
        return this.parse(tokens.skip(), name);
    }

    private Parse<Let> parse(TokenSeq tokens, String name) {
        return this.typeParser.parse(tokens).then((type, tokenSeq) -> {
            if (!tokenSeq.current().isSymbol(Symbol.EQUAL)) {
                return new Parse<Let>(new BadSyntax(tokenSeq, "="));
            }
            return this.parseExpression(tokenSeq, name, type);
        });
    }

    private Parse<Let> parseExpression(TokenSeq tokens, String name, Type type) {
        if (tokens.skip().eof()) {
            return new Parse<Let>(new UnexpectedEof(tokens));
        }
        return this.expressionParser.parse(tokens.skip()).then((expression, tokenSeq) -> {
            return this.checkTypes(tokens, tokenSeq, name, type, expression);
        });
    }

    private Parse<Let> checkTypes(TokenSeq letTokens, TokenSeq tokens, String name, Type type, Expression expr) {
        if (!expr.getType().equals(type)) {
            return new Parse<Let>(new TypeMismatch(tokens, type, expr.getType()));
        }
        return new Parse<Let>(new Let(name, type, expr), tokens);
    }

}
