package lorikeet.lobe.articletesting.data.deserialize;

import lorikeet.Dict;
import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.lobe.articletesting.data.ObjectValue;
import lorikeet.lobe.articletesting.reader.TextReader;
import lorikeet.lobe.articletesting.data.Value;
import lorikeet.error.CommaMustComeAfterFieldInObjectValue;
import lorikeet.error.CouldNotDeserializeObjectFieldValue;
import lorikeet.error.InconclusiveError;
import lorikeet.error.LorikeetException;
import lorikeet.error.ObjectValueClassNameMustBeFollowedByOpenParenthesis;
import lorikeet.error.ObjectValueFieldMustBeAValidAlphaNumericWord;
import lorikeet.error.ObjectValueFieldMustBeFollowedByColon;
import lorikeet.error.ObjectValueMustStartWithClassName;
import lorikeet.error.UnexpectedEndOfContentWhileParsing;

public class ObjectValueDeserializer implements ValueDeserializer<ObjectValue> {

    private final Deserializer deserializer;
    private final boolean directDeserialization;

    public ObjectValueDeserializer() {
        this.deserializer = new Deserializer();
        this.directDeserialization = true;
    }

    public ObjectValueDeserializer(boolean directDeserialization) {
        this.deserializer = new Deserializer();
        this.directDeserialization = directDeserialization;
    }

    @Override
    public Err<ObjectValue> deserialize(TextReader reader) {
        final Err<String> className = reader.nextIdentifier();
        if (!className.isPresent()) {
            return Err.failure(this.potentiallyInconclusive(new ObjectValueMustStartWithClassName()));
        }
        if (reader.isAtEnd()) {
            return Err.failure(new UnexpectedEndOfContentWhileParsing());
        }
        if (reader.getCurrentChar() != '(') {
            return Err.failure(this.potentiallyInconclusive(new ObjectValueClassNameMustBeFollowedByOpenParenthesis()));
        }
        reader.skip();
        Dict<String, Value> data = Dict.empty();
        while (true) {
            if (reader.isAtEnd()) {
                return Err.failure(new UnexpectedEndOfContentWhileParsing());
            }
            if (reader.getCurrentChar() == ',') {
                if (!data.isEmpty()) {
                    reader.skip();
                } else {
                    return Err.failure(new CommaMustComeAfterFieldInObjectValue());
                }
            }
            if (reader.getCurrentChar() == ')') {
                reader.skip();
                return Err.of(new ObjectValue(className.orPanic(), data));
            }
            final Opt<String> field = reader.nextAlphaNumericWord(false);
            if (!field.isPresent()) {
                return Err.failure(new ObjectValueFieldMustBeAValidAlphaNumericWord());
            }
            if (reader.getCurrentChar() != ':') {
                return Err.failure(new ObjectValueFieldMustBeFollowedByColon());
            }
            reader.skip();
            final Err<Value> value = deserializer.deserialize(reader);
            if (!value.isPresent()) {
                return Err.failure(new CouldNotDeserializeObjectFieldValue());
            }
            data = data.push(field.orPanic(), value.orPanic());
        }
    }

    private LorikeetException potentiallyInconclusive(LorikeetException err) {
        if (this.directDeserialization) {
            return err;
        }
        return new InconclusiveError(err);
    }
}
