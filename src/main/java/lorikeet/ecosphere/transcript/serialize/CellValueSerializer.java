package lorikeet.ecosphere.transcript.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.CellValue;
import lorikeet.ecosphere.transcript.Value;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CellValueSerializer implements ValueSerializer {

    private final Serializer serializer = new Serializer();

    @Override
    public Opt<String> serialize(Value value) {
        if (value == null || !(value instanceof CellValue)) {
            return Opt.empty();
        }

        final CellValue cell = (CellValue)value;

        StringBuilder serialized = new StringBuilder();
        serialized.append("<");
        serialized.append(cell.getClassName());
        serialized.append(" ");

        final List<Map.Entry<String, Value>> entryList = cell.getArguments()
            .entrySet()
            .stream()
            .sorted(Comparator.comparing(Map.Entry::getKey))
            .collect(Collectors.toList());

        for (Map.Entry<String, Value> arg : entryList) {
            serialized.append(arg.getKey());
            serialized.append("=");
            serialized.append(this.serializer.serialize(arg.getValue()));
            serialized.append(" ");
        }

        cell.getReturnValue().then(returnValue -> {
           serialized.append("-return=");
           serialized.append(this.serializer.serialize(returnValue));
           serialized.append(" ");
        });

        cell.getExceptionThrown().then(exception -> {
            serialized.append("-exception=");
            serialized.append(this.serializer.serialize(exception));
            serialized.append(" ");
        });

        serialized.append(">");
        return Opt.of(serialized.toString());
    }
}
