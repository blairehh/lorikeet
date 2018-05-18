package lorikeet.transpile;

import lorikeet.lang.Package;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaUtilsTest {

    @Test
    public void testSubdirecotry1() {
        assertThat(JavaUtils.subdirectoryFor(new Package("com"))).isEqualTo("com/");
    }

    @Test
    public void testSubdirecotry2() {
        assertThat(JavaUtils.subdirectoryFor(new Package("com", "compnay")))
            .isEqualTo("com/compnay/");
    }

    @Test
    public void testSubdirecotry3() {
        assertThat(JavaUtils.subdirectoryFor(new Package("com", "compnay", "product")))
            .isEqualTo("com/compnay/product/");
    }
}
