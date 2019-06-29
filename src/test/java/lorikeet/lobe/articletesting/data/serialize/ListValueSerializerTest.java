package lorikeet.lobe.articletesting.data.serialize;


import lorikeet.Seq;
import lorikeet.lobe.articletesting.data.ListValue;
import lorikeet.lobe.articletesting.data.NumberValue;
import lorikeet.lobe.articletesting.data.StringValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ListValueSerializerTest {

    private final ListValueSerializer serializer = new ListValueSerializer();

    @Test
    public void testEmptyList() {
        assertThat(serializer.serialize(new ListValue(Seq.empty())).orPanic())
            .isEqualTo("[]");
    }

    @Test
    public void testOneInt() {
        assertThat(serializer.serialize(new ListValue(Seq.of(new NumberValue(1)))).orPanic())
            .isEqualTo("[1]");
    }

    @Test
    public void testTwoChars() {
        assertThat(serializer.serialize(new ListValue(Seq.of(new StringValue("a"), new StringValue("b")))).orPanic())
            .isEqualTo("['a', 'b']");
    }

    @Test
    public void testCollectionOfCollections() {
        ListValue collection = new ListValue(Seq.of(
            new ListValue(Seq.of(new NumberValue(1), new NumberValue(2))),
            new ListValue(Seq.of(new NumberValue(3), new NumberValue(4)))
        ));
        assertThat(serializer.serialize(collection).orPanic())
            .isEqualTo("[[1, 2], [3, 4]]");
    }

}