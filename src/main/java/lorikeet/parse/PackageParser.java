package lorikeet.parse;

import lorikeet.error.InvalidPackageName;
import lorikeet.error.UnexpectedEof;
import lorikeet.lang.Package;
import lorikeet.token.TokenSeq;
import lorikeet.token.Symbol;

import java.util.List;
import java.util.ArrayList;


public class PackageParser implements Parser<Package> {

    @Override
    public Parse<Package> parse(TokenSeq tokens) {
        return this.parse(tokens, new ArrayList<String>());
    }

    public Parse<Package> parse(TokenSeq tokens, List<String> hierarchy) {
        if (tokens.eof()) {
            return new Parse<Package>(new UnexpectedEof(tokens));
        }

        if (!Patterns.isPackageName(tokens.currentStr())) {
            return new Parse<Package>(new InvalidPackageName(tokens));
        }
        hierarchy.add(tokens.currentStr());

        if (tokens.shift().eof()) {
            return new Parse<Package>(new Package(hierarchy), tokens.shift());
        }
        if (tokens.next().isNewLine()) {
            return new Parse<Package>(new Package(hierarchy), tokens.jump());
        }
        if (tokens.next().isSymbol(Symbol.DOT)) {
            return this.parse(tokens.shift().skip(), hierarchy);
        }
        return new Parse<Package>(new Package(hierarchy), tokens); 
    }

}
