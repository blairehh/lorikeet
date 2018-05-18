package lorikeet.parse;

import lorikeet.error.CompileError;
import lorikeet.error.BadSyntax;
import lorikeet.error.UnexpectedEof;
import lorikeet.error.AttributeNameConflict;
import lorikeet.error.MissingSeparator;
import lorikeet.lang.Attribute;
import lorikeet.lang.Package;
import lorikeet.lang.Module;
import lorikeet.lang.Type;
import lorikeet.token.TokenSeq;
import lorikeet.token.Tokenizer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ModuleParserTest {

    private final Tokenizer tokenizer = new Tokenizer();
    private final ModuleParser parser = new ModuleParser(new TypeTable(), new Package("app"));

    @Test
    public void testOneMember() {
        final String code = "module Database = conn JdbConnection";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Module module = expect(parser.parse(tokens));
        expect(module, new Type(new Package("app"), "Database"));
        expect(module, 1);
        expect(module, "conn", new Type(new Package("app"), "JdbConnection"));
    }

    @Test
    public void testTwoMembers() {
        final String code = "module Database = host Str, port Int";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Module module = expect(parser.parse(tokens));
        expect(module, new Type(new Package("app"), "Database"));
        expect(module, 2);
        expect(module, "host", new Type(new Package("lorikeet", "core"), "Str"));
        expect(module, "port", new Type(new Package("lorikeet", "core"), "Int"));
    }

    @Test
    public void testThreeMembersAllNewlines() {
        final String code = "module Foo = \n    a A\n    b B\n    c C";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Module module = expect(parser.parse(tokens));
        expect(module, new Type(new Package("app"), "Foo"));
        expect(module, 3);
        expect(module, "a", new Type(new Package("app"), "A"));
        expect(module, "b", new Type(new Package("app"), "B"));
        expect(module, "c", new Type(new Package("app"), "C"));
    }

    @Test
    public void testNoAttributesAndMainWorks() {
        final String code = "module main;";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Module module = expect(parser.parse(tokens));
        expect(module, new Type(new Package("app"), "main"));
        expect(module, 0);
    }

    @Test
    public void testMainWithAttributes() {
        final String code = "module main = \n io Io";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Module module = expect(parser.parse(tokens));
        expect(module, new Type(new Package("app"), "main"));
        expect(module, "io", new Type(new Package("app"), "Io"));
        expect(module, 1);
    }

    @Test
    public void testFailsWithoutEqualOrColon() {
        final String code = "module main io Io";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();
        expect(parser.parse(tokens), BadSyntax.class, "io");
    }

    @Test
    public void testFailsAttributeNameConflict() {
        final String code = "module main = io Io, io Io";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();
        expect(parser.parse(tokens), AttributeNameConflict.class);
    }

    @Test
    public void testMustHaveCommaIfMoreThanOneWord() {
        final String code = "module main = io Io max Int";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();
        expect(parser.parse(tokens), MissingSeparator.class);
    }

    Module expect(Parse<Module> parse) {
        assertThat(parse.succeded()).isTrue();
        return parse.getResult();
    }

    void expect(Parse<Module> parse, Class<? extends CompileError> error, String token) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(error)).isTrue();
        assertThat(((BadSyntax)parse.getErrors(0)).getFound()).isEqualTo(token);
    }

    void expect(Parse<Module> parse, Class<? extends CompileError> error) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(error)).isTrue();
    }

    void expect(Module module, Type type) {
        assertThat(module.getType()).isEqualTo(type);
    }

    void expect(Module module, int attrCount) {
        assertThat(module.getAttributes().size()).isEqualTo(attrCount);
    }

    void expect(Module module, String name, Type type) {
        assertThat(module.getAttributes().contains(new Attribute(name, type))).isTrue();
    }
}
