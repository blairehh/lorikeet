package lorikeet.ecosphere.articletesting.data.deserialize;

import lorikeet.Dict;
import lorikeet.Err;
import lorikeet.ecosphere.articletesting.data.CellDefinition;
import lorikeet.ecosphere.articletesting.data.Value;
import lorikeet.ecosphere.articletesting.reader.TextReader;
import lorikeet.error.ArticleCellMustStartWithIdentifier;
import lorikeet.error.CouldNotDeserializeExceptionClassofCell;
import lorikeet.error.CouldNotDeserializeReturnValueOfCell;
import lorikeet.error.CouldNotDeserializeArgumentValueOfCell;
import lorikeet.error.OpenParenthesisMustBeFollowedByCellClassName;
import lorikeet.error.UnexpectedEndOfContentWhileParsing;
import lorikeet.error.UnexpectedTokenWhileParsing;

public class ArticleCellDeserializer {

    private final Deserializer deserializer = new Deserializer();

    public Err<CellDefinition> deserialize(TextReader reader) {
        final Err<String> className = reader.nextIdentifier();
        if (!className.isPresent()) {
            return Err.failure(new ArticleCellMustStartWithIdentifier());
        }

        if (reader.getCurrentChar() != '(') {
            return Err.failure(new OpenParenthesisMustBeFollowedByCellClassName());
        }
        reader.skip();


        Dict<String, Value> arguments = Dict.empty();
        int argumentCounter = 0;


        while (true) {
            if (reader.isAtEnd()) {
                return Err.failure(new UnexpectedEndOfContentWhileParsing());
            }

            if (reader.getCurrentChar() == ')') {
                reader.skip();
                return deserializeReturnsOrThrows(reader, className.orPanic(), arguments);
            }

            final Err<Value> argument= this.deserializer.deserialize(reader);
            if (!argument.isPresent()) {
                return Err.failure(new CouldNotDeserializeArgumentValueOfCell());
            }
            arguments = arguments.push(String.valueOf(argumentCounter), argument.orPanic());

            if (reader.getCurrentChar() == ')') {
                reader.skip();
                return deserializeReturnsOrThrows(reader, className.orPanic(), arguments);
            }

            if (reader.getCurrentChar() == ',') {
                argumentCounter += 1;
                reader.skip();
                continue;
            }

            return Err.failure(new UnexpectedTokenWhileParsing());

        }
    }

    private Err<CellDefinition> deserializeReturnsOrThrows(TextReader reader, String className, Dict<String, Value> arguments) {
        String exceptionThrown = null;
        Value returnValue = null;

        final String word = reader.nextWord().orElse("");

        if (word.equalsIgnoreCase("returns")) {
            final Err<Value> value = this.deserializer.deserialize(reader);
            if (!value.isPresent()) {
                return Err.failure(new CouldNotDeserializeReturnValueOfCell());
            }
            returnValue = value.orPanic();
        }

        if (word.equalsIgnoreCase("throws")) {
            final Err<String> exceptionClass = reader.nextIdentifier();
            if (!exceptionClass.isPresent()) {
                return Err.failure(new CouldNotDeserializeExceptionClassofCell());
            }
            exceptionThrown = exceptionClass.orPanic();
        }

        return Err.of(new CellDefinition(className, arguments, exceptionThrown, returnValue));
    }
}
