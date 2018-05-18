package lorikeet.parse;

import lorikeet.error.CompileError;
import lorikeet.error.InvalidTypeName;
import lorikeet.error.UndefinedType;
import lorikeet.lang.Package;
import lorikeet.lang.Type;
import lorikeet.lang.Use;
import lorikeet.token.TokenSeq;
import lorikeet.token.Tokenizer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeParserTest {

    private static Tokenizer tokenizer = new Tokenizer();
    private static TypeTable table = new TypeTable();
    private TypeParser parser;

    static {
        table.add(new Use(new Package("foo", "bar"), "Book", "Book"));
        table.add(new Use(new Package("foo", "bar"), "Writer", "Author"));
    }

    @Test
    public void testBasicDefaultsToPackage() {
        parser = new TypeParser(table, new Package("org", "test"));

        final String code = "User";
        final TokenSeq tokens = tokenizer.tokenize(code);

        final Type type = expect(parser.parse(tokens));
        expect(type, "User", "org", "test");
    }

    @Test
    public void testTypeForLorikeetCore() {
        parser = new TypeParser(table, new Package("org", "test"));

        final String code = "Str";
        final TokenSeq tokens = tokenizer.tokenize(code);

        final Type type = expect(parser.parse(tokens));
        expect(type, "Str", "lorikeet", "core");
    }

    @Test
    public void testTypeFromTable() {
        parser = new TypeParser(table, new Package("org", "test"));

        final String code = "Book";
        final TokenSeq tokens = tokenizer.tokenize(code);

        final Type type = expect(parser.parse(tokens));
        expect(type, "Book", "foo", "bar");
    }

    @Test
    public void testTypeFromTableAlias() {
        parser = new TypeParser(table, new Package("org", "test"));

        final String code = "Author";
        final TokenSeq tokens = tokenizer.tokenize(code);

        final Type type = expect(parser.parse(tokens));
        expect(type, "Writer", "foo", "bar");
    }

    @Test
    public void testFailsIfMustFIndInTypeTable() {
        parser = new TypeParser(table, new Package("org", "test"), false);

        final String code = "Publisher";
        final TokenSeq tokens = tokenizer.tokenize(code);

        expect(parser.parse(tokens), UndefinedType.class);
    }

    @Test
    public void testInvalidTypeName() {
        parser = new TypeParser(table, new Package("org", "test"), false);

        final String code = "publisher";
        final TokenSeq tokens = tokenizer.tokenize(code);

        expect(parser.parse(tokens), InvalidTypeName.class);
    }


    Type expect(Parse<Type> parse) {
        assertThat(parse.succeded()).isTrue();
        assertThat(parse.getTokenSeq().eof()).isTrue();
        return parse.getResult();
    }

    void expect(Parse<Type> parse, Class<? extends CompileError> compileError) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(compileError)).isTrue();
    }

    void expect(Type def, String name, String... pkg) {
        assertThat(def.getName()).isEqualTo(name);
        assertThat(def.getPackage()).isEqualTo(new Package(pkg));
    }

}
