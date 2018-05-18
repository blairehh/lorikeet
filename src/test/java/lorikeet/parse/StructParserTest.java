package lorikeet.parse;

import lorikeet.error.CompileError;
import lorikeet.error.BadSyntax;
import lorikeet.error.AttributeNameConflict;
import lorikeet.error.MissingSeparator;
import lorikeet.lang.Attribute;
import lorikeet.lang.Package;
import lorikeet.lang.Struct;
import lorikeet.lang.Type;
import lorikeet.token.TokenSeq;
import lorikeet.token.Tokenizer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StructParserTest {

    private final Tokenizer tokenizer = new Tokenizer();
    private final StructParser parser = new StructParser(new TypeTable(), new Package("test"));

    @Test
    public void testOneMember() {
        final String code = "struct User = name Str";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Struct struct = expect(parser.parse(tokens));
        expect(struct, new Type(new Package("test"), "User"));
        expect(struct, 1);
        expect(struct, "name", new Type(new Package("lorikeet", "core"), "Str"));
    }

    @Test
    public void testTwoMembers() {
        final String code = "struct User = name Str, age Int";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Struct struct = expect(parser.parse(tokens));
        expect(struct, new Type(new Package("test"), "User"));
        expect(struct, 2);
        expect(struct, "name", new Type(new Package("lorikeet", "core"), "Str"));
        expect(struct, "age", new Type(new Package("lorikeet", "core"), "Int"));
    }

    @Test
    public void testThreeMembers() {
        final String code = "struct User = name Str, height Dec, pos Coordinate";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Struct struct = expect(parser.parse(tokens));
        expect(struct, new Type(new Package("test"), "User"));
        expect(struct, 3);
        expect(struct, "name", new Type(new Package("lorikeet", "core"), "Str"));
        expect(struct, "height", new Type(new Package("lorikeet", "core"), "Dec"));
        expect(struct, "pos", new Type(new Package("test"), "Coordinate"));
    }

    @Test
    public void testThreeMembersWithNewline() {
        final String code = "struct User = name Str, height Dec\n pos Coordinate";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Struct struct = expect(parser.parse(tokens));
        expect(struct, new Type(new Package("test"), "User"));
        expect(struct, 3);
        expect(struct, "name", new Type(new Package("lorikeet", "core"), "Str"));
        expect(struct, "height", new Type(new Package("lorikeet", "core"), "Dec"));
        expect(struct, "pos", new Type(new Package("test"), "Coordinate"));
    }

    @Test
    public void testThreeMembersAllNewlines() {
        final String code = "struct User = \n    name Str\n    height Dec\n    pos Coordinate";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Struct struct = expect(parser.parse(tokens));
        expect(struct, new Type(new Package("test"), "User"));
        expect(struct, 3);
        expect(struct, "name", new Type(new Package("lorikeet", "core"), "Str"));
        expect(struct, "height", new Type(new Package("lorikeet", "core"), "Dec"));
        expect(struct, "pos", new Type(new Package("test"), "Coordinate"));
    }

    @Test
    public void testNoAttributes() {
        final String code = "struct User;";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Struct struct = expect(parser.parse(tokens));
        expect(struct, new Type(new Package("test"), "User"));
        expect(struct, 0);
    }

    @Test
    public void testEndedByAnotherStruct() {
        final String code = "struct User = \n    name Str\n    height Dec\n    pos Coordinate\nstruct User";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Struct struct = expect(parser.parse(tokens));
        expect(struct, new Type(new Package("test"), "User"));
        expect(struct, 3);
        expect(struct, "name", new Type(new Package("lorikeet", "core"), "Str"));
        expect(struct, "height", new Type(new Package("lorikeet", "core"), "Dec"));
        expect(struct, "pos", new Type(new Package("test"), "Coordinate"));
    }

    @Test
    public void testFailsWithoutEqualOrColon() {
        final String code = "struct User name";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();
        expect(parser.parse(tokens), BadSyntax.class, "name");
    }

    @Test
    public void testFailsAttributeNameConflict() {
        final String code = "struct User = foo Int, bar Int, foo Int";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();
        expect(parser.parse(tokens), AttributeNameConflict.class);
    }

    @Test
    public void testMustHaveCommaIfMoreThanOneWord() {
        final String code = "struct User = foo Int, bar Int baz Int";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();
        expect(parser.parse(tokens), MissingSeparator.class);
    }

    Struct expect(Parse<Struct> parse) {
        assertThat(parse.succeded()).isTrue();
        return parse.getResult();
    }

    void expect(Parse<Struct> parse, Class<? extends CompileError> error, String token) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(error)).isTrue();
        assertThat(((BadSyntax)parse.getErrors(0)).getFound()).isEqualTo(token);
    }

    void expect(Parse<Struct> parse, Class<? extends CompileError> error) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(error)).isTrue();
    }

    void expect(Struct struct, Type type) {
        assertThat(struct.getType()).isEqualTo(type);
    }

    void expect(Struct struct, int attrCount) {
        assertThat(struct.getAttributes().size()).isEqualTo(attrCount);
    }

    void expect(Struct struct, String name, Type type) {
        assertThat(struct.getAttributes().contains(new Attribute(name, type))).isTrue();
    }
}
