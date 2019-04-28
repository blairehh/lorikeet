package lorikeet.ecosphere.meta;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Dbg {
    String value() default "";
    boolean useHash() default false;
    boolean ignore() default false;
}
