package lorikeet.parse;

import lorikeet.error.DuplicatePackageDeclaration;
import lorikeet.error.PackageMustBeDeclared;
import lorikeet.error.UndefinedType;
import lorikeet.error.NotBindableType;
import lorikeet.lang.Function;
import lorikeet.lang.Module;
import lorikeet.lang.Package;
import lorikeet.lang.SourceFile;
import lorikeet.lang.Struct;
import lorikeet.lang.Type;
import lorikeet.token.TokenSeq;
import lorikeet.token.Keyword;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/*
* @TODO remove not bindable and instead add the func to a local "bind bin".
* @TODO if type not found assume in package and check later
*/
public class SourceFileParser {

    public Parse<SourceFile> parse(TokenSeq tokens) {
        if (!tokens.current().isKeyword(Keyword.PACKAGE)) {
            return new Parse<SourceFile>(new PackageMustBeDeclared(tokens));
        }
        return new PackageParser().parse(tokens.skip()).then((pkg, tokenSeq) -> {
            return this.parse(tokenSeq, new ParseData(pkg));
        });
    }

    private Parse<SourceFile> parse(TokenSeq tokens, ParseData data) {
        if (tokens.eof()) {
            return new Parse<SourceFile>(new SourceFile(data.pkg, data.structs, data.modules), tokens);
        }
        return this.parseDeclarations(tokens, data);
    }

    private Parse<SourceFile> parseDeclarations(TokenSeq tokens, ParseData data) {
        if (tokens.eof()) {
            return new Parse<SourceFile>(new SourceFile(data.pkg, data.structs, data.modules), tokens);
        }

        if (tokens.current().isKeyword(Keyword.STRUCT)) {
            return this.struct(tokens.skip(), data);
        }
        if (tokens.current().isKeyword(Keyword.USE)) {
            return this.use(tokens.skip(), data);
        }
        if (tokens.current().isKeyword(Keyword.FUNC)) {
            return this.func(tokens.skip(), data);
        }
        if (tokens.current().isKeyword(Keyword.MODULE)) {
            return this.module(tokens.skip(), data);
        }

        if (tokens.current().isKeyword(Keyword.PACKAGE)) {
            return new Parse<SourceFile>(new DuplicatePackageDeclaration(tokens));
        }
        return new Parse<SourceFile>(new SourceFile(data.pkg, data.structs, data.modules), tokens);
    }

    private Parse<SourceFile> struct(TokenSeq tokens, ParseData data) {
        final StructParser parser = new StructParser(data.typeTable, data.pkg);
        return parser.parse(tokens).then((struct, tokenSeq) -> {
            data.structs.add(struct);
            data.typeTable.add(struct.getType());
            return this.parse(tokenSeq, data);
        });
    }

    private Parse<SourceFile> use(TokenSeq tokens, ParseData data) {
        return new UseParser().parse(tokens).then((uses, tokenSeq) -> {
            uses.forEach(use -> data.typeTable.add(use));
            return this.parse(tokenSeq, data);
        });
    }

    private Parse<SourceFile> func(TokenSeq tokens, ParseData data) {
        final FunctionParser parser = new FunctionParser(data.typeTable, data.pkg);
        return parser.parse(tokens).then((func, tokenSeq) -> {
            if (!data.typeTable.get(func.getType()).isPresent()) {
                return new Parse<SourceFile>(new UndefinedType(tokens));
            }
            return this.funcBody(tokenSeq, func, data);
        });
    }

    private Parse<SourceFile> funcBody(TokenSeq tokens, Function func, ParseData data) {
        final ExpressionParser expressionParser = new ExpressionParser(
            new VariableTable(func),
            new TypeParser(data.typeTable, data.pkg),
            Arrays.asList("struct", "module", "expr", "def", "algebraic", "func", "package")
        );
        return expressionParser.parse(tokens).then((expression, tokenSeq) -> {
            if (!data.addFunctionToType(func.withExpression(expression))) {
                return new Parse<SourceFile>(new NotBindableType(tokens, func));
            }
            return this.parse(tokenSeq, data);
        });
    }

    private Parse<SourceFile> module(TokenSeq tokens, ParseData data) {
        ModuleParser parser = new ModuleParser(data.typeTable, data.pkg);
        return parser.parse(tokens).then((module, tokenSeq) -> {
            data.modules.add(module);
            data.typeTable.add(module.getType());
            return this.parse(tokenSeq, data);
        });
    }

    private static class ParseData {
        public final Package pkg;
        public final List<Struct> structs = new ArrayList<Struct>();
        public final List<Module> modules = new ArrayList<Module>();
        public final TypeTable typeTable = new TypeTable();

        public ParseData(Package pkg) {
            this.pkg = pkg;
        }

        public boolean addFunctionToType(Function func) {
            return (
                this.addFunctionToStruct(func)
                || this.addFunctionToModule(func)
            );
        }

        private boolean addFunctionToStruct(Function func) {
            Struct struct = this.structs
                .stream()
                .filter(s -> s.getType().equals(func.getType()))
                .findFirst()
                .orElse(null);
            if (struct == null) {
                return false;
            }
            struct.addFunction(func);
            return true;
        }

        private boolean addFunctionToModule(Function func) {
            Module module = this.modules
                .stream()
                .filter(m -> m.getType().equals(func.getType()))
                .findFirst()
                .orElse(null);
            if (module == null) {
                return false;
            }
            module.addFunction(func);
            return true;
        }
    }

}
