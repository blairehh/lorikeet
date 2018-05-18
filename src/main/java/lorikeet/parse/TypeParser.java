package lorikeet.parse;

import lorikeet.error.InvalidTypeName;
import lorikeet.error.UndefinedType;
import lorikeet.error.UnexpectedEof;
import lorikeet.lang.Package;
import lorikeet.lang.Type;
import lorikeet.token.TokenSeq;
import lorikeet.token.TokenType;


/*
* @TODO add support for generics
*/
public class TypeParser implements Parser<Type> {

    private final boolean defaultToPackage;
    private final TypeTable table;
    private final Package pkg;

    public TypeParser(TypeTable table, Package pkg, boolean defaultToPackage) {
        this.pkg = pkg;
        this.table = table;
        this.defaultToPackage = defaultToPackage;
    }

    public TypeParser(TypeTable table, Package pkg) {
        this.pkg = pkg;
        this.table = table;
        this.defaultToPackage = true;
    }

    public Parse<Type> parse(TokenSeq tokens) {
        if (tokens.eof()) {
            return new Parse<Type>(new UnexpectedEof(tokens));
        }

        if (!Patterns.isTypeName(tokens.currentStr())) {
            return new Parse<Type>(new InvalidTypeName(tokens.current(), tokens));
        }
        String typeName = tokens.currentStr();

        if (this.table == null) {
            return new Parse<Type>(new Type(this.pkg, typeName), tokens.skip());
        }
        return this.table.get(typeName)
            .map(typeEntry -> new Parse<Type>(typeEntry.getType(), tokens.skip()))
            .orElseGet(() -> this.resortToDefault(tokens, typeName));
    }

    private Parse<Type> resortToDefault(TokenSeq tokens, String typeName) {
        if (!this.defaultToPackage) {
            return new Parse<Type>(new UndefinedType(tokens));
        }
        return new Parse<Type>(new Type(this.pkg, typeName), tokens.skip());
    }

}
