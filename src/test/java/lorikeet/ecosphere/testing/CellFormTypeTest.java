package lorikeet.ecosphere.testing;

import lorikeet.ecosphere.Action1;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CellFormTypeTest {

    @Test
    public void testFromName() {
        assertThat(CellFormType.fromName("foobar").isPresent()).isFalse();
        assertThat(CellFormType.fromName("action1").orPanic()).isEqualTo(CellFormType.ACTION_1);
    }

    @Test
    public void testFromJavaClass() {
        assertThat(CellFormType.fromJavaClass(String.class).isPresent()).isFalse();
        assertThat(CellFormType.fromJavaClass(Action1.class).orPanic()).isEqualTo(CellFormType.ACTION_1);
    }
}
