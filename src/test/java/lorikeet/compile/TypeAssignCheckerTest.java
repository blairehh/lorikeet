package lorikeet.compile;

import lorikeet.error.CompileError;
import lorikeet.lang.LorikeetSource;
import lorikeet.lang.SourceFile;
import lorikeet.lang.Function;
import lorikeet.lang.Let;
import lorikeet.parse.Parse;
import lorikeet.parse.SourceFileParser;
import lorikeet.token.TokenSeq;
import lorikeet.token.Tokenizer;
import static lorikeet.Lang.*;
import static lorikeet.Util.read;

import java.util.Collections;
import java.util.ArrayList;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class TypeAssignCheckerTest {

    private final Tokenizer tokenizer = new Tokenizer();
    private final SourceFileParser parser = new SourceFileParser();
    private final TypeAssignChecker checker = new TypeAssignChecker();

    @Test
    public void testTypeMismatchForFunc() {
        final TokenSeq tokens = tokenize("testTypeMismatchForFunc.lk");

        final LorikeetSource source = expect(parser.parse(tokens));
        final TypeRegistry registry = registryFor(source);

        checker.check(source, registry);

        SourceFile sf = source.getResults().get(0);
        Function baz = sf.findFunction("Foo", "baz").get();
        Let let = baz.findLet("doh").get();
        System.out.println("--------------");
        System.out.println(let.toString());
        // expect(sourceFile, 2, 0);
    }

    TypeRegistry registryFor(LorikeetSource source) {
        final TypeRegistry registry = new TypeRegistry();
        for (SourceFile f : source.getResults()) {
            registry.enter(f);
        }
        return registry;
    }

    LorikeetSource expect(Parse<SourceFile> parse) {
        assertThat(parse.succeded()).isTrue();
        return new LorikeetSource(
            Collections.singletonList(parse.getResult()),
            new ArrayList<CompileError>()
        );
    }

    TokenSeq tokenize(String diskFile) {
        final String contents = read("lorikeet/compile/TypeAssignCheckerTest/" + diskFile);
        return tokenizer.tokenize(contents);
    }
}
