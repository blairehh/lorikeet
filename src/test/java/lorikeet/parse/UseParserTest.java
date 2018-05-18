package lorikeet.parse;

import lorikeet.lang.Package;
import lorikeet.lang.Use;
import lorikeet.token.TokenSeq;
import lorikeet.token.Tokenizer;

import java.util.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UseParserTest {

    private final Tokenizer tokenizer = new Tokenizer();
    private final UseParser parser = new UseParser();

    @Test
    public void testBasic() {
        final String code = "use Car of foo.bar";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final List<Use> uses = expect(parser.parse(tokens));
        expect(uses, 1);
        expect(uses, 0, "Car", "Car", "foo", "bar");
    }


    @Test
    public void test2() {
        final String code = "use Car Bike of foo.bar";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final List<Use> uses = expect(parser.parse(tokens));
        expect(uses, 2);
        expect(uses, 0, "Car", "Car", "foo", "bar");
        expect(uses, 1, "Bike", "Bike", "foo", "bar");
    }

    @Test
    public void test3WithComma() {
        final String code = "use Car, Bike, Truck of foo.bar";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final List<Use> uses = expect(parser.parse(tokens));
        expect(uses, 3);
        expect(uses, 0, "Car", "Car", "foo", "bar");
        expect(uses, 1, "Bike", "Bike", "foo", "bar");
        expect(uses, 2, "Truck", "Truck", "foo", "bar");
    }

    @Test
    public void testAlias() {
        final String code = "use Car as Bil of foo.bar";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final List<Use> uses = expect(parser.parse(tokens));
        expect(uses, 1);
        expect(uses, 0, "Car", "Bil", "foo", "bar");
    }

    @Test
    public void testMultiMixedAlias() {
        final String code = "use Car as Bil, Truck, Bike as Bicycle of foo.bar";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final List<Use> uses = expect(parser.parse(tokens));
        expect(uses, 3);
        expect(uses, 0, "Car", "Bil", "foo", "bar");
        expect(uses, 1, "Truck", "Truck", "foo", "bar");
        expect(uses, 2, "Bike", "Bicycle", "foo", "bar");
    }


    List<Use> expect(Parse<List<Use>> parse) {
        assertThat(parse.succeded()).isTrue();
        assertThat(parse.getTokenSeq().eof()).isTrue();
        return parse.getResult();
    }

    void expect(List<Use> uses, int count) {
        assertThat(uses.size()).isEqualTo(count);
    }

    void expect(List<Use> uses, int index, String name, String alias, String... pkg) {
        assertThat(uses.get(index).getName()).isEqualTo(name);
        assertThat(uses.get(index).getAlias()).isEqualTo(alias);
        assertThat(uses.get(index).getPackage()).isEqualTo(new Package(pkg));
    }
}
