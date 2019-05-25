package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.testing.data.ListValue;
import lorikeet.ecosphere.testing.data.TextReader;
import lorikeet.ecosphere.testing.data.Value;

public class ListValueDeserializer implements ValueDeserializer<ListValue> {

    private final Deserializer deserializer = new Deserializer();

    @Override
    public Opt<ListValue> deserialize(TextReader text) {
        text.jumpWhitespace();
        if (text.getCurrentChar() != '[') {
            return Opt.empty();
        }
        text.skip();
        Seq<Value> values = Seq.empty();
        while (true) {
            if (text.isAtEnd()) {
                Opt.empty();
            }
            final Opt<Value> value = deserializer.deserialize(text);
            if (!value.isPresent()) {
                return Opt.empty();
            }
            values = values.push(value);
            text.jumpWhitespace();
            if (text.getCurrentChar() == ']') {
                text.skip();
                return Opt.of(new ListValue(values));
            }
            if (text.getCurrentChar() == ',') {
                text.skip();
                continue;
            }
        }
    }
}
