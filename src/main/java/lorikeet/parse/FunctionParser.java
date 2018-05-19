package lorikeet.parse;

import lorikeet.error.InvalidFunctionName;
import lorikeet.error.UnexpectedEof;
import lorikeet.error.BadSyntax;
import lorikeet.error.AttributeNameConflict;
import lorikeet.lang.Attribute;
import lorikeet.lang.Function;
import lorikeet.lang.Package;
import lorikeet.lang.Type;
import lorikeet.token.Symbol;
import lorikeet.token.TokenSeq;

import java.util.Set;
import java.util.LinkedHashSet;

/*
@TODO allow attributes to be specified on multiple lines
*/
public class FunctionParser implements Parser<Function> {

    private final Package pkg;
    private final TypeTable typeTable;
    private final TypeParser typeParser;
    private final AttributeParser attrParser;

    public FunctionParser(TypeTable typeTable, Package pkg) {
        this.typeTable = typeTable;
        this.pkg = pkg;
        this.typeParser = new TypeParser(typeTable, pkg, false);
        this.attrParser = new AttributeParser(this.typeParser);
    }

    @Override
    public Parse<Function> parse(TokenSeq tokens) {
        if (tokens.eof()) {
            return new Parse<Function>(new UnexpectedEof(tokens));
        }
        if (tokens.currentStr().equals("main")) {
            return this.parse(tokens.skip(), new Type(this.pkg, "main"));
        }
        return this.typeParser.parse(tokens).then((type, tokenSeq) -> {
            return this.parse(tokenSeq, type);
        });
    }

    private Parse<Function> parse(TokenSeq tokens, Type type) {
        if (tokens.eof()) {
            return new Parse<Function>(new UnexpectedEof(tokens));
        }
        if (!tokens.current().isSymbol(Symbol.FORWARD_SLASH)) {
            return new Parse<Function>(new BadSyntax(tokens, "/"));
        }
        TokenSeq nextSeq = tokens.skip();
        if (nextSeq.eof()) {
            return new Parse<Function>(new UnexpectedEof(nextSeq));
        }
        if (!Patterns.isFunctionName(nextSeq.currentStr())) {
            return new Parse<Function>(new InvalidFunctionName(nextSeq));
        }
        return this.parseParamsOrReturn(nextSeq.skip(), type, nextSeq.currentStr());
    }

    private Parse<Function> parseParamsOrReturn(TokenSeq tokens, Type type, String name) {
        if (tokens.current().isSymbol(Symbol.OPEN_ROUND)) {
            return this.parseParams(tokens.jump(), type, name);
        }
        return this.parseReturn(tokens, type, name);
    }

    private Parse<Function> parseParams(TokenSeq tokens, Type type, String name) {
        return this.parseParam(tokens, type, name, new LinkedHashSet<Attribute>());
    }

    private Parse<Function> parseParam(TokenSeq tokens, Type type, String name, Set<Attribute> attrs) {
        if (tokens.eof()) {
            return new Parse<Function>(new UnexpectedEof(tokens));
        }
        if (tokens.current().isSymbol(Symbol.CLOSE_ROUND)) {
            return this.parseReturn(tokens.skip(), type, name, attrs);
        }
        return this.attrParser.parse(tokens).then((attr, tokenSeq) -> {
            final long count = attrs.stream()
                .filter(attribute -> attribute.getName().equals(attr.getName()))
                .count();
            if (count != 0) {
                return new Parse<Function>(new AttributeNameConflict(tokens, attr));
            }
            attrs.add(attr);
            if (tokenSeq.current().isSymbol(Symbol.CLOSE_ROUND)) {
                return this.parseReturn(tokenSeq.skip(), type, name, attrs);
            }
            if (tokenSeq.current().isSymbol(Symbol.COMMA)) {
                return this.parseParam(tokenSeq.skip(), type, name, attrs);
            }
            return new Parse<Function>(new BadSyntax(tokenSeq, ",", ")"));
        });
    }

    private Parse<Function> parseReturn(TokenSeq tokens, Type type, String name) {
        return this.parseReturn(tokens, type, name, new LinkedHashSet<Attribute>());
    }

    private Parse<Function> parseReturn(TokenSeq tokens, Type type, String name, Set<Attribute> attrs) {
        if (tokens.current().isSymbol(Symbol.EQUAL)) {
            final Type voidType = new Type(new Package("java", "lang"), "Void");
            return new Parse<Function>(new Function(type, name, attrs, voidType), tokens.skip());
        }
        return this.typeParser.parse(tokens).then((returnType, tokenSeq) -> {
            return this.parseEnd(tokenSeq, type, name, attrs, returnType);
        });
    }

    private Parse<Function> parseEnd(TokenSeq tokens, Type type, String name, Set<Attribute> attrs, Type returnType) {
        if (tokens.eof()) {
            return new Parse<Function>(new UnexpectedEof(tokens));
        }
        if (!tokens.current().isSymbol(Symbol.EQUAL)) {
            return new Parse<Function>(new BadSyntax(tokens, "="));
        }
        return new Parse<Function>(new Function(type, name, attrs, returnType), tokens.skip());
    }
}
