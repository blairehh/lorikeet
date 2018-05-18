package lorikeet.parse;

import lorikeet.error.BadSyntax;
import lorikeet.error.UnexpectedEof;
import lorikeet.error.AttributeNameConflict;
import lorikeet.error.MissingSeparator;
import lorikeet.lang.Attribute;
import lorikeet.lang.Package;
import lorikeet.lang.Struct;
import lorikeet.lang.Type;
import lorikeet.token.TokenSeq;
import lorikeet.token.TokenType;
import lorikeet.token.Symbol;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Collections;

public class StructParser implements Parser<Struct> {


    private final Package pkg;
    private final TypeTable typeTable;
    private final TypeParser typeParser;
    private final AttributeParser attrParser;

    public StructParser(TypeTable table, Package pkg) {
        this.pkg = pkg;
        this.typeTable = table;
        this.typeParser = new TypeParser(table, pkg);
        this.attrParser = new AttributeParser(table, pkg);
    }

    public Parse<Struct> parse(TokenSeq tokens) {
        return this.typeParser.parse(tokens)
            .then((type, tokenSeq) -> this.parseStructAssignment(tokenSeq, type));
    }

    private Parse<Struct> parseStructAssignment(TokenSeq tokens, Type type) {
        if (tokens.current().isSymbol(Symbol.SEMICOLON)) {
            return new Parse<Struct>(
                new Struct(tokens.getFile(), type, Collections.emptySet()), tokens.jump()
            );
        }

        if (!tokens.current().isSymbol(Symbol.EQUAL)) {
            return new Parse<Struct>(new BadSyntax(tokens, "=", ";"));
        }
        return this.parseAttributes(tokens.jump(), type);
    }

    private Parse<Struct> parseAttributes(TokenSeq tokens, Type type) {
        LinkedHashSet<Attribute> attrs = new LinkedHashSet<Attribute>();
        return this.parseAttributes(tokens, type, attrs);
    }

    private Parse<Struct> parseAttributes(TokenSeq tokens, Type type, Set<Attribute> attrs) {
        if (tokens.eof() && attrs.size() == 0) {
            return new Parse<Struct>(new UnexpectedEof(tokens));
        }
        if (tokens.eof()) {
            return new Parse<Struct>(new Struct(tokens.getFile(), type, attrs), tokens);
        }
        if (tokens.current().getTokenType() == TokenType.KEYWORD) {
            return new Parse<Struct>(new Struct(tokens.getFile(), type, attrs), tokens);
        }

        return this.attrParser.parse(tokens).then((attr, tokenSeq) -> {
            final long count = attrs.stream()
                .filter(attribute -> attribute.getName().equals(attr.getName()))
                .count();
            if (count != 0) {
                return new Parse<Struct>(new AttributeNameConflict(tokens, attr));
            }
            attrs.add(attr);
            return this.parsePostAttribute(tokenSeq, type, attrs);
        });
    }

    private Parse<Struct> parsePostAttribute(TokenSeq tokens, Type type, Set<Attribute> attrs) {
        if (tokens.eof()) {
            return new Parse<Struct>(new Struct(tokens.getFile(), type, attrs), tokens.toLast());
        }

        if (tokens.current().getTokenType() == TokenType.KEYWORD) {
            return new Parse<Struct>(new Struct(tokens.getFile(), type, attrs), tokens);
        }

        if (tokens.current().isNewLine() || tokens.current().isSymbol(Symbol.COMMA)) {
            return this.parseAttributes(tokens.jump(), type, attrs);
        }

        return new Parse<Struct>(new MissingSeparator(tokens));
    }

}
