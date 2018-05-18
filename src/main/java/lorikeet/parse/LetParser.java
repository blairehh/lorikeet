package lorikeet.parse;

import lorikeet.error.InvalidVariableName;
import lorikeet.error.BadSyntax;
import lorikeet.error.UnexpectedEof;
import lorikeet.error.UnsupportedQuotation;
import lorikeet.error.LiteralMismatch;
import lorikeet.error.IntCannotBeDecimal;
import lorikeet.error.NotImplementedYet;
import lorikeet.error.VariableNameConflict;
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

    public LetParser(VariableTable varTable, TypeTable typeTable, Package pkg) {
        this.typeParser = new TypeParser(typeTable, pkg);
        this.varTable = varTable;
    }

    public LetParser(VariableTable varTable, TypeParser typeParser) {
        this.typeParser = typeParser;
        this.varTable = varTable;
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
            return this.parse(tokenSeq.skip(), name, type);
        });
    }

    private Parse<Let> parse(TokenSeq tokens, String name, Type type) {
        if (tokens.eof()) {
            return new Parse<Let>(new UnexpectedEof(tokens));
        }

        if (tokens.current().getTokenType() == TokenType.QUOTATION) {
            return this.parseQuotation(tokens, name, type);
        }

        if (tokens.current().getTokenType() == TokenType.NUMBER) {
            return this.parseNumeric(tokens, name, type);
        }

        if (tokens.currentStr().equals("true") || tokens.currentStr().equals("false")) {
            return this.parseBoolean(tokens, name, type);
        }
        return new Parse<Let>(new NotImplementedYet(tokens));
    }

    private Parse<Let> parseQuotation(TokenSeq tokens, String name, Type type) {
        QuotationToken token = (QuotationToken)tokens.current();
        if (token.getQuote().equals("`")) {
            return new Parse<Let>(new UnsupportedQuotation(tokens));
        }
        if (!type.equals(Str.type())) {
            return new Parse<Let>(new LiteralMismatch(tokens, type));
        }
        return new Parse<Let>(new Let(name, type, new StrLiteral(tokens.currentStr())), tokens.jump());
    }

    private Parse<Let> parseBoolean(TokenSeq tokens, String name, Type type) {
        if (!type.equals(Bol.type())) {
            return new Parse<Let>(new LiteralMismatch(tokens, type));
        }
        return new Parse<Let>(new Let(name, type, new BolLiteral(tokens.currentStr())), tokens.jump());
    }

    private Parse<Let> parseNumeric(TokenSeq tokens, String name, Type type) {
        if (tokens.currentStr().contains(".")) {
            if (type.equals(Int.type())) {
                return new Parse<Let>(new IntCannotBeDecimal(tokens));
            }
            return new Parse<Let>(new Let(name, type, new DecLiteral(tokens.currentStr())), tokens.jump());
        }
        return new Parse<Let>(new Let(name, type, new IntLiteral(tokens.currentStr())), tokens.jump());
    }


}
