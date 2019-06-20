package lorikeet.ecosphere.articletesting.data.deserialize;

import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.articletesting.data.GenericSymbolicValue;
import lorikeet.ecosphere.articletesting.data.Value;
import lorikeet.ecosphere.articletesting.reader.TextReader;
import lorikeet.error.InconclusiveError;
import lorikeet.error.LorikeetException;
import lorikeet.error.SymbolicValueMustStartWithAtSymbol;
import lorikeet.error.SymbolicValueNameMustOnlyBeLetters;
import lorikeet.error.UnexpectedTokenWhileParsing;

public class GenericSymbolicDeserializer {
    private final Deserializer deserializer;
    private final boolean directDeserializatoin;

    public GenericSymbolicDeserializer() {
        this.deserializer = new Deserializer();
        this.directDeserializatoin = true;
    }

    public GenericSymbolicDeserializer(boolean directDeserializatoin) {
        this.deserializer = new Deserializer();
        this.directDeserializatoin = directDeserializatoin;
    }

    public Err<GenericSymbolicValue> deserialize(TextReader reader) {
        reader.jumpWhitespace();

        if (reader.getCurrentChar() != '@') {
            final LorikeetException error = this.directDeserializatoin
                ? new SymbolicValueMustStartWithAtSymbol()
                : new InconclusiveError(new SymbolicValueMustStartWithAtSymbol());
            return Err.failure(error);
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
