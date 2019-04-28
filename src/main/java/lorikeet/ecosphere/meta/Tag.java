package lorikeet.ecosphere.meta;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;

@Retention(RetentionPolicy.RUNTIME)
@Target( { PARAMETER })
public @interface Tag {
    String value();
    boolean useHash() default false;
    boolean ignore() default false;
}
