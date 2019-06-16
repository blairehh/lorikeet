package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.testing.data.ListValue;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.error.CouldNotDeserializeValueInListValue;
import lorikeet.error.ListValueMustStartWithOpenBracket;
import lorikeet.error.UnexpectedEndOfContentWhileParsing;

public class ListValueDeserializer implements ValueDeserializer<ListValue> {

    private final Deserializer deserializer = new Deserializer();

    @Override
    public Err<ListValue> deserialize(TextReader text) {
        text.jumpWhitespace();
        if (text.getCurrentChar() != '[') {
            return Err.failure(new ListValueMustStartWithOpenBracket());
        }
        text.skip();
        Seq<Value> values = Seq.empty();
        while (true) {
            if (text.isAtEnd()) {
                return Err.failure(new UnexpectedEndOfContentWhileParsing());
            }
            final Err<Value> value = deserializer.deserialize(text);
            if (!value.isPresent()) {
                return Err.failure(new CouldNotDeserializeValueInListValue());
            }
            values = values.push(value);
            text.jumpWhitespace();
            if (text.getCurrentChar() == ']') {
                text.skip();
                return Err.of(new ListValue(values));
            }
            if (text.getCurrentChar() == ',') {
                text.skip();
                continue;
            }
        }
    }
}
