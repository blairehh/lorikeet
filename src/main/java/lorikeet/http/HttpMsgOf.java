package lorikeet.http;

import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.IncludableFallible;
import lorikeet.core.Ok;
import lorikeet.http.error.MsgTypeDidNotHaveAnnotatedCtor;
import lorikeet.http.error.UnsupportedHeaderValueType;
import lorikeet.lobe.IncomingHttpMsg;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class HttpMsgOf<T> implements IncludableFallible<T> {
    private final IncomingHttpMsg msg;
    private final Class<T> msgClass;

    public HttpMsgOf(IncomingHttpMsg msg, Class<T> msgClass) {
        this.msg = msg;
        this.msgClass = msgClass;
    }

    @Override
    public Fallible<T> include() {
        return this.findCtor()
            .map(this::include)
            .orElse(new Err<>(new MsgTypeDidNotHaveAnnotatedCtor(this.msgClass)));
    }

    private Fallible<T> include(Constructor<T> ctor) {
        Object[] parameterValues = new Object[ctor.getParameters().length];
        for (int i = 0; i < ctor.getParameters().length; i++) {
            final Parameter parameter = ctor.getParameters()[i];
            final Header header = parameter.getAnnotation(Header.class);
            if (header != null) {
                final Fallible<?> result = this.handleHeader(parameter, header);
                if (result.failure()) {
                    return new Err<>((Err<?>)result);
                }
                parameterValues[i] = result.orPanic();
            }
        }
        try {
            return new Ok<>(ctor.newInstance(parameterValues));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return new Err<>(e);
        }
    }

    private Fallible<?> handleHeader(Parameter parameter, Header header) {
        if (parameter.getType().equals(Integer.class)) {
            return new IntHeader(this.msg, header.value()).include();
        }
        if (parameter.getType().equals(Double.class)) {
            return new DoubleHeader(this.msg, header.value()).include();
        }
        if (parameter.getType().equals(Long.class)) {
            return new LongHeader(this.msg, header.value()).include();
        }
        if (parameter.getType().equals(Boolean.class)) {
            return new BoolHeader(this.msg, header.value()).include();
        }
        if (parameter.getType().equals(String.class)) {
            return new StringHeader(this.msg, header.value()).include();
        }
        return new Err<>(new UnsupportedHeaderValueType(parameter.getType()));
    }

    private Optional<Constructor<T>> findCtor() {
        return Arrays.asList(this.msgClass.getConstructors())
            .stream()
            .filter((ctor) -> ctor.getDeclaredAnnotations().length != 0)
            .map((ctor) -> (Constructor<T>)ctor)
            .findFirst();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        HttpMsgOf<?> httpMsgOf = (HttpMsgOf<?>) o;

        return Objects.equals(this.msg, httpMsgOf.msg)
            && Objects.equals(this.msgClass, httpMsgOf.msgClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.msg, this.msgClass);
    }
}
