package lorikeet.ecosphere.testing;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ParameterSerializerSupportTest {

    private final ParameterSerializer serializer = new ParameterSerializer(ParameterSerializationCapabilityRegistry.init());

    @Test
    public void testEmptyList() {
        assertThat(ParameterSerializerSupport.serializeCollection(Collections.emptyList(), this.getClass(), serializer))
            .isEqualTo("[]");
    }

    @Test
    public void testOneInt() {
        assertThat(ParameterSerializerSupport.serializeCollection(Arrays.asList(1), this.getClass(), serializer))
            .isEqualTo("[1]");
    }

    @Test
    public void testTwoChars() {
        assertThat(ParameterSerializerSupport.serializeCollection(Arrays.asList('a', 'b'), this.getClass(), serializer))
            .isEqualTo("['a', 'b']");
    }

    @Test
    public void testCollectionOfCollections() {
        Collection<Set<Integer>> collection = Arrays.asList(
            new HashSet<>(Arrays.asList(1, 2)),
            new HashSet<>(Arrays.asList(3, 4))
        );
        assertThat(ParameterSerializerSupport.serializeCollection(collection, this.getClass(), serializer))
            .isEqualTo("[[1, 2], [3, 4]]");
    }


    @Test
    public void testEmptyMap() {
        Map<String, String> map = new HashMap<>();
        assertThat(ParameterSerializerSupport.serializeMap(map, this.getClass(), serializer)).isEqualTo("{}");
    }

    @Test
    public void testMapOneEntry() {
        Map<String, String> map = new HashMap<>();
        map.put("foo", "bar");
        assertThat(ParameterSerializerSupport.serializeMap(map, this.getClass(), serializer))
            .isEqualTo("{\"foo\": \"bar\"}");
    }

    @Test
    public void testMapTwoEntries() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        assertThat(ParameterSerializerSupport.serializeMap(map, this.getClass(), serializer))
            .isEqualTo("{1: 2, 3: 4}");
    }
}