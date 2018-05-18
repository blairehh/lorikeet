package lorikeet.parse;

import lorikeet.error.CompileError;
import lorikeet.error.InvalidName;
import lorikeet.lang.Attribute;
import lorikeet.lang.Package;
import lorikeet.lang.Struct;
import lorikeet.lang.Type;
import lorikeet.token.TokenSeq;
import lorikeet.token.Tokenizer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AttributeParserTest {

    private final Tokenizer tokenizer = new Tokenizer();
    private final AttributeParser parser = new AttributeParser(new TypeTable(), new Package("test"));

    @Test
    public void testBasic() {
        final String code = "name Str";
        final TokenSeq tokens = tokenizer.tokenize(code);

        final Attribute attr = expect(parser.parse(tokens));
        expect(attr, "name");
        expect(attr, new Type(new Package("lorikeet", "core"), "Str"));
    }

    @Test
    public void testInvalidName() {
        expect(parser.parse(tokenizer.tokenize("Foo")), InvalidName.class);
        expect(parser.parse(tokenizer.tokenize("_foo")), InvalidName.class);
        expect(parser.parse(tokenizer.tokenize("$foo")), InvalidName.class);
        expect(parser.parse(tokenizer.tokenize("5oo")), InvalidName.class);
    }

    Attribute expect(Parse<Attribute> parse) {
        assertThat(parse.succeded()).isTrue();
        assertThat(parse.getTokenSeq().eof()).isTrue();
        return parse.getResult();
    }

    void expect(Parse<Attribute> parse, Class<? extends CompileError> error) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(error)).isTrue();
    }

    void expect(Attribute attr, String name) {
        assertThat(attr.getName()).isEqualTo(name);
    }

    void expect(Attribute attr, Type type) {
        assertThat(attr.getType()).isEqualTo(type);
    }
}
