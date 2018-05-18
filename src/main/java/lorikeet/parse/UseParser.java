package lorikeet.parse;

import lorikeet.error.UnexpectedEof;
import lorikeet.error.InvalidTypeName;
import lorikeet.lang.Package;
import lorikeet.lang.Use;
import lorikeet.token.Keyword;
import lorikeet.token.Symbol;
import lorikeet.token.TokenSeq;
import lorikeet.token.TokenType;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UseParser implements Parser<List<Use>> {

    public Parse<List<Use>> parse(TokenSeq tokens) {
        if (tokens.eof()) {
            return new Parse<List<Use>>(new UnexpectedEof(tokens));
        }
        List<Thing> things = new ArrayList<Thing>();
        return parseThings(tokens, things);
    }

    private Parse<List<Use>> parseThings(TokenSeq tokens, List<Thing> things) {
        if (!Patterns.isTypeName(tokens.currentStr())) {
            return new Parse<List<Use>>(new InvalidTypeName(tokens));
        }

        TokenSeq nextSeq = tokens.skip();

        if (nextSeq.eof()) {
            return new Parse<List<Use>>(new UnexpectedEof(tokens));
        }

        if (nextSeq.current().isKeyword(Keyword.AS)) {
            nextSeq = nextSeq.skip();
            if (!Patterns.isTypeName(nextSeq.currentStr())) {
                return new Parse<List<Use>>(new InvalidTypeName(tokens));
            }
            things.add(new Thing(tokens.currentStr(), nextSeq.currentStr()));
            nextSeq = nextSeq.skip();
        } else {
            things.add(new Thing(tokens.currentStr()));
        }

        if (nextSeq.current().isSymbol(Symbol.COMMA)) {
            return parseThings(nextSeq.skip(), things);
        }

        if (nextSeq.current().isKeyword(Keyword.OF)) {
            return parsePackage(nextSeq.skip(), things);
        }

        return parseThings(nextSeq, things);
    }

    private Parse<List<Use>> parsePackage(TokenSeq tokens, List<Thing> things) {
        PackageParser pkgParser = new PackageParser();
        return pkgParser.parse(tokens).then((pkg, tokenSeq) -> {
            List<Use> uses = things.stream()
                .map(thing -> thing.toUse(pkg))
                .collect(Collectors.toList());

            return new Parse<List<Use>>(uses, tokenSeq);
        });
    }


    private static class Thing {
        final String name;
        final String alias;

        public Thing(String name) {
            this.name = name;
            this.alias = name;
        }

        public Thing(String name, String alias) {
            this.name = name;
            this.alias = alias;
        }

        public Use toUse(Package pkg) {
            return new Use(pkg, this.name, this.alias);
        }
    }

}
