package lorikeet.data;

import lorikeet.data.DataSerializer;
import lorikeet.data.DataSerializationSupport;
import lorikeet.data.DataSerializationCapabilityRegistry;
import lorikeet.ecosphere.Account;
import lorikeet.ecosphere.User;
import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DataSerializerSupportTest {

    private final DataSerializer serializer = new DataSerializer(DataSerializationCapabilityRegistry.init());

    @Test
    public void testEmptyList() {
        assertThat(DataSerializationSupport.serializeCollection(Collections.emptyList(), this.getClass(), serializer))
            .isEqualTo("[]");
    }

    @Test
    public void testOneInt() {
        assertThat(DataSerializationSupport.serializeCollection(Arrays.asList(1), this.getClass(), serializer))
            .isEqualTo("[1]");
    }

    @Test
    public void testTwoChars() {
        assertThat(DataSerializationSupport.serializeCollection(Arrays.asList('a', 'b'), this.getClass(), serializer))
            .isEqualTo("['a', 'b']");
    }

    @Test
    public void testCollectionOfCollections() {
        Collection<Set<Integer>> collection = Arrays.asList(
            new HashSet<>(Arrays.asList(1, 2)),
            new HashSet<>(Arrays.asList(3, 4))
        );
        assertThat(DataSerializationSupport.serializeCollection(collection, this.getClass(), serializer))
            .isEqualTo("[[1, 2], [3, 4]]");
    }


    @Test
    public void testEmptyMap() {
        Map<String, String> map = new HashMap<>();
        assertThat(DataSerializationSupport.serializeMap(map, this.getClass(), serializer)).isEqualTo("{}");
    }

    @Test
    public void testMapOneEntry() {
        Map<String, String> map = new HashMap<>();
        map.put("foo", "bar");
        assertThat(DataSerializationSupport.serializeMap(map, this.getClass(), serializer))
            .isEqualTo("{\"foo\": \"bar\"}");
    }

    @Test
    public void testMapTwoEntries() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        assertThat(DataSerializationSupport.serializeMap(map, this.getClass(), serializer))
            .isEqualTo("{1: 2, 3: 4}");
    }

    @Test
    public void testObject() {
        User user = new User();
        user.email = "foo@gmail.com";
        user.password = "secret";

        assertThat(DataSerializationSupport.serializeObject(user, this.getClass(), serializer))
            .isEqualTo("lorikeet.ecosphere.User(email=\"foo@gmail.com\", password=\"secret\", welcomeMessageSentAt=null, account=null)");
    }

    @Test
    public void testObjectWithObjectAsMember() {
        User user = new User();
        user.email = "foo@gmail.com";
        user.password = "secret";

        Account account = new Account();
        account.balance = 45.33;
        user.account = account;

        assertThat(DataSerializationSupport.serializeObject(user, this.getClass(), serializer))
            .isEqualTo("lorikeet.ecosphere.User(email=\"foo@gmail.com\", password=\"secret\", welcomeMessageSentAt=null, account=lorikeet.ecosphere.Account(id=\"FG5464\", balance=45.33))");
    }
}