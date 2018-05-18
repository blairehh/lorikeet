package lorikeet.parse;

import lorikeet.error.CompileError;
import lorikeet.error.InvalidPackageName;
import lorikeet.lang.Package;
import lorikeet.token.TokenSeq;
import lorikeet.token.Tokenizer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PackageParserTest {

    private final Tokenizer tokenizer = new Tokenizer();
    private final PackageParser parser = new PackageParser();

    @Test
    public void testOne() {
        final String code = "app";
        final TokenSeq tokens = tokenizer.tokenize(code);

        final Package pkg = expect(parser.parse(tokens));
        expect(pkg, 1);
        expect(pkg, 0, "app");
    }

    @Test
    public void testTwo() {
        final String code = "com.tech";
        final TokenSeq tokens = tokenizer.tokenize(code);

        final Package pkg = expect(parser.parse(tokens));
        expect(pkg, 2);
        expect(pkg, 0, "com");
        expect(pkg, 1, "tech");
    }

    @Test
    public void testThree() {
        final String code = "com.te5ch.foo9";
        final TokenSeq tokens = tokenizer.tokenize(code);

        final Package pkg = expect(parser.parse(tokens));

        expect(pkg, 3);
        expect(pkg, 0, "com");
        expect(pkg, 1, "te5ch");
        expect(pkg, 2, "foo9");
    }

    @Test
    public void testBadInvalidName() {
        final String code = "com._foo.foo";
        final TokenSeq tokens = tokenizer.tokenize(code);

        expect(parser.parse(tokens), InvalidPackageName.class);
    }


    Package expect(Parse<Package> parse) {
        assertThat(parse.succeded()).isTrue();
        assertThat(parse.getTokenSeq().eof()).isTrue();
        return parse.getResult();
    }

    void expect(Parse<Package> parse, Class<? extends CompileError> compileError) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(compileError)).isTrue();
    }

    void expect(Package pkg, int count) {
        assertThat(pkg.getHierarchy().size()).isEqualTo(count);
    }

    void expect(Package pkg, int index, String value) {
        assertThat(pkg.getHierarchy().get(index)).isEqualTo(value);
    }

}
