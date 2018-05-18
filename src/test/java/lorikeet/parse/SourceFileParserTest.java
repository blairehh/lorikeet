package lorikeet.parse;

import static lorikeet.Util.read;

import lorikeet.error.CompileError;
import lorikeet.error.DuplicatePackageDeclaration;
import lorikeet.error.PackageMustBeDeclared;
import lorikeet.error.UndefinedType;
import lorikeet.error.NotBindableType;
import lorikeet.lang.Attribute;
import lorikeet.lang.Function;
import lorikeet.lang.Module;
import lorikeet.lang.Package;
import lorikeet.lang.SourceFile;
import lorikeet.lang.Struct;
import lorikeet.lang.Type;
import lorikeet.token.TokenSeq;
import lorikeet.token.Tokenizer;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SourceFileParserTest {

    private final Tokenizer tokenizer = new Tokenizer();
    private final SourceFileParser parser = new SourceFileParser();

    @Test
    public void testOneStruct() {
        final TokenSeq tokens = tokenize("oneStruct.lk");

        final SourceFile sourceFile = expect(parser.parse(tokens));
        expect(sourceFile, 1, 0);

        HashSet<Attribute> attrs = new HashSet<Attribute>();
        attrs.add(new Attribute("name", new Type(new Package("lorikeet", "core"), "Str")));
        attrs.add(new Attribute("rank", new Type(new Package("lorikeet", "core"), "Dec")));
        attrs.add(new Attribute("active", new Type(new Package("lorikeet", "core"), "Bol")));
        expect(sourceFile, new Struct(new Type(new Package("app"), "User"), attrs));
    }

    @Test
    public void testOneStructWithFunctions() {
        final TokenSeq tokens = tokenize("oneStructWithFunctions.lk");

        final SourceFile sourceFile = expect(parser.parse(tokens));
        expect(sourceFile, 1, 0);

        HashSet<Attribute> attrs = new HashSet<Attribute>();
        attrs.add(new Attribute("name", new Type(new Package("lorikeet", "core"), "Str")));
        attrs.add(new Attribute("rank", new Type(new Package("lorikeet", "core"), "Dec")));
        attrs.add(new Attribute("active", new Type(new Package("lorikeet", "core"), "Bol")));

        Function func1 = new Function(
            new Type(new Package("app"), "User"),
            "isPro",
            new LinkedHashSet<Attribute>(),
            new Type(new Package("lorikeet", "core"), "Bol")
        );
        Function func2 = new Function(
            new Type(new Package("app"), "User"),
            "isNoob",
            new LinkedHashSet<Attribute>(),
            new Type(new Package("lorikeet", "core"), "Bol")
        );


        expect(
            sourceFile,
            new Struct(new Type(new Package("app"), "User"), attrs, Arrays.asList(func1, func2))
        );
    }

    @Test
    public void testTwoStructs() {
        final TokenSeq tokens = tokenize("twoStructs.lk");

        final SourceFile sourceFile = expect(parser.parse(tokens));
        expect(sourceFile, 2, 0);

        HashSet<Attribute> attrs = new HashSet<Attribute>();
        attrs.add(new Attribute("name", new Type(new Package("lorikeet", "core"), "Str")));
        attrs.add(new Attribute("rank", new Type(new Package("lorikeet", "core"), "Dec")));
        attrs.add(new Attribute("active", new Type(new Package("lorikeet", "core"), "Bol")));
        expect(sourceFile, new Struct(new Type(new Package("app"), "User"), attrs));


        attrs = new HashSet<Attribute>();
        attrs.add(new Attribute("password", new Type(new Package("lorikeet", "core"), "Str")));
        attrs.add(new Attribute("username", new Type(new Package("lorikeet", "core"), "Str")));
        expect(sourceFile, new Struct(new Type(new Package("app"), "Credentials"), attrs));
    }

    @Test
    public void testOneStructWithUse() {
        final TokenSeq tokens = tokenize("oneStructWithUse.lk");

        final SourceFile sourceFile = expect(parser.parse(tokens));
        expect(sourceFile, 1, 0);

        HashSet<Attribute> attrs = new HashSet<Attribute>();
        attrs.add(new Attribute("name", new Type(new Package("lorikeet", "core"), "Str")));
        attrs.add(new Attribute("rank", new Type(new Package("lorikeet", "core"), "Dec")));
        attrs.add(new Attribute("role", new Type(new Package("types"), "Role")));
        expect(sourceFile, new Struct(new Type(new Package("app"), "User"), attrs));
    }

    @Test
    public void testMultipleStructsMultipleUse() {
        final TokenSeq tokens = tokenize("multipleStructsMultipleUse.lk");

        final SourceFile sourceFile = expect(parser.parse(tokens));
        expect(sourceFile, 2, 0);

        HashSet<Attribute> attrs = new HashSet<Attribute>();
        attrs.add(new Attribute("name", new Type(new Package("lorikeet", "core"), "Str")));
        attrs.add(new Attribute("credentials", new Type(new Package("app", "stuff"), "Credentials")));
        attrs.add(new Attribute("role", new Type(new Package("com", "company", "core"), "Role")));
        expect(sourceFile, new Struct(new Type(new Package("app", "stuff"), "User"), attrs));

        attrs = new HashSet<Attribute>();
        attrs.add(new Attribute("password", new Type(new Package("security"), "HashedPassword")));
        attrs.add(new Attribute("username", new Type(new Package("lorikeet", "core"), "Str")));
        expect(sourceFile, new Struct(new Type(new Package("app", "stuff"), "Credentials"), attrs));
    }

    @Test
    public void testUsesStructsModulesFunctions() {
        final TokenSeq tokens = tokenize("usesStructsModulesFunctions.lk");

        final SourceFile sourceFile = expect(parser.parse(tokens));
        expect(sourceFile, 1, 1);

        LinkedHashSet<Attribute> attrs1 = new LinkedHashSet<Attribute>();
        attrs1.add(new Attribute("id", new Type(new Package("lorikeet", "core"), "Int")));
        attrs1.add(new Attribute("name", new Type(new Package("lorikeet", "core"), "Str")));
        attrs1.add(new Attribute("active", new Type(new Package("lorikeet", "core"), "Bol")));

        LinkedHashSet<Attribute> params1 = new LinkedHashSet<Attribute>();
        params1.add(new Attribute("f", new Type(new Package("org", "io"), "File")));
        Function func1 = new Function(
            new Type(new Package("app"), "Record"),
            "fromFile",
            params1,
            new Type(new Package("app"), "Record")
        );
        expect(
            sourceFile,
            new Struct(new Type(new Package("app"), "Record"), attrs1, Arrays.asList(func1))
        );

        LinkedHashSet<Attribute> attrs2 = new LinkedHashSet<Attribute>();
        Function func2 = new Function(
            new Type(new Package("app"), "main"),
            "run",
            new LinkedHashSet<Attribute>(),
            new Type(new Package("lorikeet", "core"), "Int")
        );
        expect(
            sourceFile,
            new Module(new Type(new Package("app"), "main"), attrs2, Arrays.asList(func2))
        );
    }

    @Test
    public void testFunctionOnUndefinedType() {
        final TokenSeq tokens = tokenize("functionOnUndefinedType.lk");
        expectUndefinedType(parser.parse(tokens), "Role");
    }

    @Test
    public void testFailsBecuaseOfNoPackage() {
        final TokenSeq tokens = tokenize("noPackage.lk");
        expectPackageMustBeDeclared(parser.parse(tokens));
    }

    @Test
    public void testFailsDuplicatePackage() {
        final TokenSeq tokens = tokenize("duplicatePackage.lk");
        expectDuplicatePackageDeclaration(parser.parse(tokens));
    }

    @Test
    public void testNotBinadbleType() {
        final TokenSeq tokens = tokenize("notBindableType.lk");
        expect(parser.parse(tokens), NotBindableType.class);
    }

    SourceFile expect(Parse<SourceFile> parse) {
        assertThat(parse.succeded()).isTrue();
        return parse.getResult();
    }

    void expectUndefinedType(Parse<SourceFile> parse, String value) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(UndefinedType.class)).isTrue();
        assertThat(((UndefinedType)parse.getErrors(0)).getValue()).isEqualTo(value);
    }

    void expect(Parse<SourceFile> parse, Class<? extends CompileError> klass) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(klass)).isTrue();
    }

    void expectPackageMustBeDeclared(Parse<SourceFile> parse) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(PackageMustBeDeclared.class)).isTrue();
    }

    void expectDuplicatePackageDeclaration(Parse<SourceFile> parse) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(DuplicatePackageDeclaration.class)).isTrue();
    }

    void expect(SourceFile sourceFile, int structCount, int moduleCount) {
        assertThat(sourceFile.getStructs().size()).isEqualTo(structCount);
        assertThat(sourceFile.getModules().size()).isEqualTo(moduleCount);
    }

    void expect(SourceFile sourceFile, Struct struct) {
        assertThat(sourceFile.getStructs().contains(struct)).isTrue();
    }

    void expect(SourceFile sourceFile, Module module) {
        assertThat(sourceFile.getModules().contains(module)).isTrue();
    }

    TokenSeq tokenize(String diskFile) {
        final String contents = read("lorikeet/parse/SourceFileParserTest/" + diskFile);
        return tokenizer.tokenize(contents);
    }


}
