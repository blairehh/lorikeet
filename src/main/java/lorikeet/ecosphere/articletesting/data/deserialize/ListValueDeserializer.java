package lorikeet.ecosphere.articletesting.data.deserialize;

import lorikeet.Err;
import lorikeet.Seq;
import lorikeet.ecosphere.articletesting.data.ListValue;
import lorikeet.ecosphere.articletesting.reader.TextReader;
import lorikeet.ecosphere.articletesting.data.Value;
import lorikeet.error.CommaMustComeAfterValueInListValue;
import lorikeet.error.CouldNotDeserializeValueInListValue;
import lorikeet.error.InconclusiveError;
import lorikeet.error.ListValueMustStartWithOpenBracket;
import lorikeet.error.UnexpectedEndOfContentWhileParsing;

public class ListValueDeserializer implements ValueDeserializer<ListValue> {

    private final Deserializer deserializer;
    private final boolean directDeserialization;

    public ListValueDeserializer() {
        this.deserializer = new Deserializer();
        this.directDeserialization = true;
    }

    public ListValueDeserializer(boolean directDeserialization) {
        this.deserializer = new Deserializer();
        this.directDeserialization = directDeserialization;
    }

    @Override
    public Err<ListValue> deserialize(TextReader text) {
        text.jumpWhitespace();
        if (text.getCurrentChar() != '[') {
            if (this.directDeserialization) {
                return Err.failure(new ListValueMustStartWithOpenBracket());
            }
            return Err.failure(new InconclusiveError(new ListValueMustStartWithOpenBracket()));
        }

        text.skip();
        Seq<Value> values = Seq.empty();
        while (true) {
            if (text.isAtEnd()) {
                return Err.failure(new UnexpectedEndOfContentWhileParsing());
            }
            if (text.getCurrentChar() == ',') {
                return Err.failure(new CommaMustComeAfterValueInListValue());
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
