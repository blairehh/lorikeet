package lorikeet.ecosphere.testing.data.serialize;

import lorikeet.Dict;
import lorikeet.ecosphere.testing.data.BoolValue;
import lorikeet.ecosphere.testing.data.ListValue;
import lorikeet.ecosphere.testing.data.MapValue;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.ecosphere.testing.data.StringValue;
import lorikeet.ecosphere.testing.data.Value;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class MapValueSerializerTest {

    private final MapValueSerializer serializer = new MapValueSerializer();

    @Test
    public void testEmptyMap() {
        MapValue map = new MapValue(Dict.empty());
        assertThat(serializer.serialize(map).orPanic()).isEqualTo("{}");
    }

    @Test
    public void testMapOneEntry() {
        MapValue map = new MapValue(Dict.of(new StringValue("foo"), new StringValue("bar")));
        assertThat(serializer.serialize(map).orPanic()).isEqualTo("{'foo': 'bar'}");
    }

    @Test
    public void testMapTwoEntries() {
        Dict<Value, Value> data = Dict.empty();
        data = data.push(new NumberValue(1), new NumberValue(2));
        data = data.push(new NumberValue(3), new NumberValue(4));

        assertThat(serializer.serialize(new MapValue(data)).orPanic()).isEqualTo("{1: 2, 3: 4}");
    }

    @Test
    public void testWithChildMapAndList() {
        Dict<Value, Value> data = Dict.empty();
        data = data.push(new MapValue(Dict.of(new StringValue("abc"), new BoolValue(true))), new ListValue(new NumberValue(0)));
        data = data.push(new ListValue(new NumberValue(2)), new MapValue(Dict.of(new StringValue("abc"), new BoolValue(true))));

        assertThat(serializer.serialize(new MapValue(data)).orPanic()).isEqualTo("{{'abc': true}: [0], [2]: {'abc': true}}");
    }

}