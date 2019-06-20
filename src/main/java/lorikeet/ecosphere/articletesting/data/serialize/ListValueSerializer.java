package lorikeet.ecosphere.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.articletesting.data.ListValue;
import lorikeet.ecosphere.articletesting.data.Value;

public class ListValueSerializer implements ValueSerializer {

    private final Serializer serializer = new Serializer();

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof ListValue)) {
            return Opt.empty();
        }

        final ListValue list = (ListValue)value;
        final int size = list.getValues().size();

        final StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Value item : list.getValues()) {

            final String serialized = serializer.serialize(item);

            builder.append(serialized);
            if (i != size - 1) {
                builder.append(", ");
            }
            i++;
        }
        builder.append("]");
        return Opt.of(builder.toString());
    }
}
