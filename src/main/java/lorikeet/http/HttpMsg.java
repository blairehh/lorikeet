package lorikeet.http;

import lorikeet.core.Bug;
import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.IncludableFallible;
import lorikeet.core.Ok;
import lorikeet.http.annotation.Delete;
import lorikeet.http.annotation.Get;
import lorikeet.http.annotation.Header;
import lorikeet.http.annotation.Headers;
import lorikeet.http.annotation.Patch;
import lorikeet.http.annotation.PathVar;
import lorikeet.http.annotation.Put;
import lorikeet.http.annotation.Query;
import lorikeet.http.error.AnnotatedHeadersMustBeOfTypeHeaderSet;
import lorikeet.http.error.FailedToConstructHttpMsg;
import lorikeet.http.error.HttpMethodDoesNotMatchRequest;
import lorikeet.http.error.HttpMsgMustHavePath;
import lorikeet.http.error.MsgTypeDidNotHaveAnnotatedCtor;
import lorikeet.http.error.UnsupportedHeaderValueType;
import lorikeet.http.error.UnsupportedPathValueType;
import lorikeet.http.error.UnsupportedQueryParameterValueType;
import lorikeet.http.internal.HeaderAnnotation;
import lorikeet.http.internal.HttpMsgPath;
import lorikeet.http.internal.IdentifierAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class HttpMsg<T> implements IncludableFallible<T> {
    private final IncomingHttpSgnl msg;
    private final Class<T> msgClass;

    public HttpMsg(IncomingHttpSgnl msg, Class<T> msgClass) {
        this.msg = msg;
        this.msgClass = msgClass;
    }

    @Override
    public Fallible<T> include() {
        return this.findCtor()
            .map(this::include)
            .orElse(new Bug<>(new MsgTypeDidNotHaveAnnotatedCtor(this.msgClass)));
    }

    @SuppressWarnings("unchecked")
    private Fallible<T> include(Constructor<T> ctor) {
        final IdentifierAnnotation id = this.retrieveIdentifierAnnotation()
            .orElse(null);
        if (id == null) {
            return new Err<>(new HttpMsgMustHavePath(this.msgClass));
        }

        if (this.msg.method() != id.method()) {
            return new Err<>(new HttpMethodDoesNotMatchRequest());
        }

        final Fallible<HttpMsgPath> pathResult = new UriPath(this.msg, id.uriPattern())
            .include();
        if (pathResult.failure()) {
            return (Fallible<T>)pathResult;
        }
        final HttpMsgPath msgPath = pathResult.orPanic();

        Object[] parameterValues = new Object[ctor.getParameters().length];
        for (int i = 0; i < ctor.getParameters().length; i++) {
            final Parameter parameter = ctor.getParameters()[i];
            final HeaderAnnotation header = this.retrieveHeaderAnnotation(parameter);
            if (header != null) {
                final Fallible<?> result = this.handleHeader(parameter, header);
                if (result.failure()) {
                    return (Fallible<T>) result;
                }
                parameterValues[i] = result.orPanic();
                continue;
            }
            final PathVar pathParam = parameter.getAnnotation(PathVar.class);
            if (pathParam != null) {
                final Fallible<?> result = this.handlePathVar(parameter, msgPath, pathParam);
                if (result.failure()) {
                    return (Fallible<T>) result;
                }
                parameterValues[i] = result.orPanic();
                continue;
            }
            final Query queryParam = parameter.getAnnotation(Query.class);
            if (queryParam != null) {
                final Fallible<?> result = this.handleQueryParam(parameter, queryParam);
                if (result.failure()) {
                    return (Fallible<T>) result;
                }
                parameterValues[i] = result.orPanic();
                continue;
            }
            final Headers headers = parameter.getAnnotation(Headers.class);
            if (headers != null) {
                if (!parameter.getType().equals(HeaderSet.class)) {
                    return new Err<>(new AnnotatedHeadersMustBeOfTypeHeaderSet(this.msgClass));
                }
                parameterValues[i] = this.msg.headers();
                continue;
            }
        }
        try {
            return new Ok<>(ctor.newInstance(parameterValues));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return new Bug<>(new FailedToConstructHttpMsg(this.msgClass, e));
        }
    }

    private HeaderAnnotation retrieveHeaderAnnotation(Parameter parameter) {
        final Header header = parameter.getAnnotation(Header.class);
        if (header != null) {
            return new HeaderAnnotation(header.value());
        }
        return null;
    }

    private Fallible<?> handleHeader(Parameter parameter, HeaderAnnotation header) {
        if (parameter.getType().equals(Integer.class) || parameter.getType().equals(int.class)) {
            return new IntHeader(this.msg, header.headerName()).include();
        }
        if (parameter.getType().equals(Double.class) || parameter.getType().equals(double.class)) {
            return new DoubleHeader(this.msg, header.headerName()).include();
        }
        if (parameter.getType().equals(Long.class) || parameter.getType().equals(long.class)) {
            return new LongHeader(this.msg, header.headerName()).include();
        }
        if (parameter.getType().equals(Boolean.class) || parameter.getType().equals(boolean.class)) {
            return new BoolHeader(this.msg, header.headerName()).include();
        }
        if (parameter.getType().equals(String.class)) {
            return new StringHeader(this.msg, header.headerName()).include();
        }
        return new Bug<>(new UnsupportedHeaderValueType(parameter.getType()));
    }

    private Fallible<?> handlePathVar(Parameter parameter, HttpMsgPath path, PathVar param) {
        if (parameter.getType().equals(Integer.class) || parameter.getType().equals(int.class)) {
            return new IntPathVar(path, param.value()).include();
        }
        if (parameter.getType().equals(Double.class) || parameter.getType().equals(double.class)) {
            return new DoublePathVar(path, param.value()).include();
        }
        if (parameter.getType().equals(Long.class) || parameter.getType().equals(long.class)) {
            return new LongPathVar(path, param.value()).include();
        }
        if (parameter.getType().equals(Boolean.class) || parameter.getType().equals(boolean.class)) {
            return new BoolPathVar(path, param.value()).include();
        }
        if (parameter.getType().equals(String.class)) {
            return new StringPathVar(path, param.value()).include();
        }
        return new Bug<>(new UnsupportedPathValueType(parameter.getType()));
    }

    private Fallible<?> handleQueryParam(Parameter parameter, Query param) {
        if (parameter.getType().equals(Integer.class) || parameter.getType().equals(int.class)) {
            return new IntQueryParam(this.msg, param.value()).include();
        }
        if (parameter.getType().equals(Double.class) || parameter.getType().equals(double.class)) {
            return new DoubleQueryParam(this.msg, param.value()).include();
        }
        if (parameter.getType().equals(Long.class) || parameter.getType().equals(long.class)) {
            return new LongQueryParam(this.msg, param.value()).include();
        }
        if (parameter.getType().equals(Boolean.class) || parameter.getType().equals(boolean.class)) {
            return new BoolQueryParam(this.msg, param.value()).include();
        }
        if (parameter.getType().equals(String.class)) {
            return new StringQueryParam(this.msg, param.value()).include();
        }
        return new Err<>(new UnsupportedQueryParameterValueType(parameter.getType()));
    }

    @SuppressWarnings("unchecked")
    private Optional<Constructor<T>> findCtor() {
        return Arrays.stream(this.msgClass.getConstructors())
            .filter((ctor) -> ctor.getDeclaredAnnotations().length != 0)
            .map((ctor) -> (Constructor<T>)ctor)
            .findFirst();
    }

    private Optional<IdentifierAnnotation> retrieveIdentifierAnnotation() {
        final Get get = this.msgClass.getAnnotation(Get.class);
        if (get != null) {
            return Optional.of(new IdentifierAnnotation(HttpMethod.GET, get.value()));
        }

        final Put put = this.msgClass.getAnnotation(Put.class);
        if (put != null) {
            return Optional.of(new IdentifierAnnotation(HttpMethod.PUT, put.value()));
        }

        final Patch patch = this.msgClass.getAnnotation(Patch.class);
        if (patch != null) {
            return Optional.of(new IdentifierAnnotation(HttpMethod.PATCH, patch.value()));
        }

        final Delete delete = this.msgClass.getAnnotation(Delete.class);
        if (delete != null) {
            return Optional.of(new IdentifierAnnotation(HttpMethod.DELETE, delete.value()));
        }

        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        HttpMsg<?> httpMsgOf = (HttpMsg<?>) o;

        return Objects.equals(this.msg, httpMsgOf.msg)
            && Objects.equals(this.msgClass, httpMsgOf.msgClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.msg, this.msgClass);
    }
}
