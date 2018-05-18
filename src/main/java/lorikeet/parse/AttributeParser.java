package lorikeet.parse;

import lorikeet.error.InvalidName;
import lorikeet.lang.Attribute;
import lorikeet.lang.Package;
import lorikeet.lang.Type;
import lorikeet.token.TokenSeq;
import lorikeet.token.TokenType;

public class AttributeParser implements Parser<Attribute> {

    private final TypeParser typeParser;

    public AttributeParser(TypeTable table, Package pkg) {
        this.typeParser = new TypeParser(table, pkg);
    }

    public AttributeParser(TypeParser typeParser) {
        this.typeParser = typeParser;
    }

    public Parse<Attribute> parse(TokenSeq tokens) {
        final String name = tokens.currentStr();
        if (!Patterns.isAttributeName(name)) {
            return new Parse<Attribute>(new InvalidName(tokens));
        }
        return this.typeParser
            .parse(tokens.skip())
            .then((type, tokenSeq) -> toParse(name, type, tokenSeq));
    }

    private static Parse<Attribute> toParse(String name, Type type, TokenSeq tokens) {
        return new Parse<Attribute>(new Attribute(name, type), tokens);
    }

}
