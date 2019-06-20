package lorikeet.ecosphere.articletesting.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
public @interface Dbg {
    String value() default "";
    boolean useHash() default false;
    boolean ignore() default false;
}
