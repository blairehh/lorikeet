package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.testing.data.GenericSymbolicValue;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.error.SymbolicValueMustStartWithAtSymbol;
import lorikeet.error.SymbolicValueNameMustOnlyBeLetters;
import lorikeet.error.UnexpectedTokenWhileParsing;

public class GenericSymbolicDeserializer {
    private final Deserializer deserializer = new Deserializer();

    public Err<GenericSymbolicValue> deserialize(TextReader reader) {
        reader.jumpWhitespace();

        if (reader.getCurrentChar() != '@') {
            return Err.failure(new SymbolicValueMustStartWithAtSymbol());
        }
        reader.skip();

        final Opt<String> name = reader.nextWord();
        if (!name.isPresent()) {
            return Err.failure(new SymbolicValueNameMustOnlyBeLetters());
        }

        Seq<Value> arguments = Seq.empty();

        if (reader.isAtEnd() || reader.getCurrentChar() != '(') {
            return Err.of(new GenericSymbolicValue(name.orPanic(), arguments));
        }

        reader.skip();

        if (reader.getCurrentChar() == ')') {
            reader.skip();
            return Err.of(new GenericSymbolicValue(name.orPanic(), arguments));
        }

        while (true) {

            final Err<Value> argument = this.deserializer.deserialize(reader);
            if (!argument.isPresent()) {
                return Err.from(argument);
            }
            arguments = arguments.push(argument);

            if (reader.getCurrentChar() == ')') {
                reader.skip();
                return Err.of(new GenericSymbolicValue(name.orPanic(), arguments));
            }

            if (reader.getCurrentChar() == ',') {
                reader.skip();
                continue;
            }

            return Err.failure(new UnexpectedTokenWhileParsing());
        }
    }
}
