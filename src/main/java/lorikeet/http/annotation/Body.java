package lorikeet.http.annotation;

import lorikeet.coding.InternetMediaType;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value=RUNTIME)
public @interface Body {
    InternetMediaType value();
}
