package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Dict;
import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.CellValue;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.error.ArticleCellMustStartWithIdentifier;
import lorikeet.error.CouldNotDeserializeValue;
import lorikeet.error.OpenParenthesisMustBeFollowedByCellClassName;
import lorikeet.error.UnexpectedEndOfContentWhileParsing;
import lorikeet.error.UnexpectedTokenWhileParsing;

public class ArticleCellDeserializer {

    private final Deserializer deserializer = new Deserializer();

    public Err<CellValue> deserialize(TextReader reader) {
        final Err<String> className = reader.nextIdentifier();
        if (!className.isPresent()) {
            return Err.failure(new ArticleCellMustStartWithIdentifier());
        }

        if (reader.getCurrentChar() != '(') {
            return Err.failure(new OpenParenthesisMustBeFollowedByCellClassName());
        }
        reader.skip();

        String exceptionThrown = null;
        Value returnValue = null;
        Dict<String, Value> arguments = Dict.empty();
        int argumentCounter = 0;


        while (true) {
            if (reader.isAtEnd()) {
                return Err.failure(new UnexpectedEndOfContentWhileParsing());
            }

            if (reader.getCurrentChar() == ')') {
                reader.skip();
                return Err.of(new CellValue(className.orPanic(), arguments, exceptionThrown, returnValue));
            }
            System.out.println(reader.getCurrentChar());
            final Opt<Value> argument= this.deserializer.deserialize(reader);
            if (!argument.isPresent()) {
                return Err.failure(new CouldNotDeserializeValue());
            }
            arguments = arguments.push(String.valueOf(argumentCounter), argument.orPanic());

            if (reader.getCurrentChar() == ')') {
                reader.skip();
                return Err.of(new CellValue(className.orPanic(), arguments, exceptionThrown, returnValue));
            }

            if (reader.getCurrentChar() == ',') {
                argumentCounter += 1;
                reader.skip();
                continue;
            }

            return Err.failure(new UnexpectedTokenWhileParsing());

        }
    }
}
